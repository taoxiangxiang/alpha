package com.alpha.manager.impl;

import com.alpha.constans.CalendarUtil;
import com.alpha.dao.LeaveDao;
import com.alpha.domain.DriverDO;
import com.alpha.domain.LeaveDO;
import com.alpha.domain.LeaveSumDO;
import com.alpha.manager.DriverManager;
import com.alpha.manager.LeaveManager;
import com.alpha.query.DriverQuery;
import com.alpha.query.LeaveQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by taoxiang on 2017/4/29.
 */
@Component("leaveManager")
public class LeaveManagerImpl implements LeaveManager {

    @Resource
    private LeaveDao leaveDao;

    @Resource
    private DriverManager driverManager;

    @Override
    public boolean insert(LeaveDO leaveDO) {
        return leaveDao.insert(leaveDO);
    }

    @Override
    public List<LeaveDO> query(LeaveQuery leaveQuery) {
        return leaveDao.query(leaveQuery);
    }

    @Override
    public int count(LeaveQuery leaveQuery) {
        return leaveDao.count(leaveQuery);
    }

    @Override
    public boolean update(LeaveDO leaveDO) {
        return leaveDao.update(leaveDO);
    }

    @Override
    public List<LeaveSumDO> getSumList(List<LeaveDO> leaveDOList, Date start, Date end) {
        List<LeaveSumDO> leaveSumDOList = new ArrayList<LeaveSumDO>();
        if (leaveDOList == null || leaveDOList.size() == 0) {
            return leaveSumDOList;
        }
        Map<Integer, Double> leaveDayMap = new HashMap<Integer, Double>();
        List<Integer> driverIdList = new ArrayList<Integer>();
        for (LeaveDO leaveDO : leaveDOList) {
            Date leaveStart = leaveDO.getStartDate();
            Date leaveEnd = leaveDO.getEndDate();
            Date startMax = leaveStart;
            Date endMinx = leaveEnd;
            if (start != null && start.after(leaveStart)) {
                startMax = start;
            }
            if (end != null && end.before(leaveStart)) {
                endMinx = end;
            }
            double day = endMinx.after(startMax) ? (endMinx.getTime() - startMax.getTime()) / 24 / 3600 / 1000 : 0;
            if (day == 0) continue;
            if (!leaveDayMap.containsKey(leaveDO.getDriverId())) {
                leaveDayMap.put(leaveDO.getDriverId(), 0D);
            }
            leaveDayMap.put(leaveDO.getDriverId(), leaveDayMap.get(leaveDO.getDriverId()) + day);
            driverIdList.add(leaveDO.getDriverId());
        }
        if (leaveDayMap.size() == 0) {
            return leaveSumDOList;
        }
        DriverQuery driverQuery = new DriverQuery();
        driverQuery.setPageSize(driverIdList.size());
        driverQuery.setIdList(driverIdList);
        List<DriverDO> driverDOList = driverManager.query(driverQuery);
        for (DriverDO driverDO : driverDOList) {
            LeaveSumDO leaveSumDO = new LeaveSumDO();
            leaveSumDO.setDriverName(driverDO.getName());
            leaveSumDO.setDriverId(driverDO.getId());
            leaveSumDO.setMobilePhone(driverDO.getMobilePhone());
            leaveSumDO.setTeam(driverDO.getTeam());
            leaveSumDO.setSex(driverDO.getSex());
            leaveSumDO.setCitizenId(driverDO.getCitizenId());
            leaveSumDO.setNumber(leaveDayMap.get(driverDO.getId()));
            leaveSumDOList.add(leaveSumDO);
        }
        return leaveSumDOList;
    }
}
