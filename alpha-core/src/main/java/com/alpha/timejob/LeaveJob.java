package com.alpha.timejob;

import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverDO;
import com.alpha.domain.LeaveDO;
import com.alpha.manager.DriverManager;
import com.alpha.manager.LeaveManager;
import com.alpha.query.LeaveQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by taoxiang on 2017/5/23.
 */
@Component("leaveJob")
public class LeaveJob implements InitializingBean {

    private static final long LOCAL_CACHE_TIMER_START = 10 * 1000;
    private static final long LOCAL_CACHE_TIMER_PERIOD = 60 * 1000;

    private static final Logger logger = LoggerFactory.getLogger(LeaveJob.class);

    @Resource
    private DriverManager driverManager;
    @Resource
    private LeaveManager leaveManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        Timer statTimer = new Timer();
        statTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                dealLeaveJob();
            }
        }, LOCAL_CACHE_TIMER_START, LOCAL_CACHE_TIMER_PERIOD);
    }

    private void dealLeaveJob() {
        try{
            LeaveQuery leaveQuery = new LeaveQuery();
            for (int i = 1; i < 100000; i++) {
                leaveQuery.setPage(i);
                leaveQuery.setPageSize(100);
                List<String> statusList = new ArrayList<String>();
                statusList.add(SystemConstant.LEAVE_VERIFY_PASS);
                leaveQuery.setStatusList(statusList);
                List<LeaveDO> leaveDOList = leaveManager.query(leaveQuery);
                if (leaveDOList == null || leaveDOList.size() == 0) {
                    break;
                }
                for (LeaveDO leaveDO : leaveDOList) {
                    dealSingleLeave(leaveDO);
                }
            }
        } catch (Exception e) {
            logger.error("LeaveJob dealLeaveJob catch exception", e);
        }
    }

    private void dealSingleLeave(LeaveDO leaveDO) {
        DriverDO driverDO = driverManager.queryById(leaveDO.getDriverId());
        if (driverDO == null) {
            return;
        }
        if (SystemConstant.DRIVER_LEAVING.equals(driverDO.getStatus()) && driverDO.getApplicationId() != null
                && driverDO.getApplicationId().intValue() == leaveDO.getId() && leaveDO.getEndDate().before(new Date())) {
            driverDO.setApplicationId(0);
            driverDO.setStatus(SystemConstant.DRIVER_CAN_USE);
            driverManager.update(driverDO);
        }

        if (SystemConstant.DRIVER_CAN_USE.equals(driverDO.getStatus()) && leaveDO.getEndDate().after(new Date())
                && leaveDO.getStartDate().before(new Date())) {
            driverDO.setApplicationId(leaveDO.getId());
            driverDO.setStatus(SystemConstant.DRIVER_LEAVING);
            driverManager.update(driverDO);
        }
    }
}
