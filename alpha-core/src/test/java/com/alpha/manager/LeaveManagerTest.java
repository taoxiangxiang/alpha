package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DepartmentDO;
import com.alpha.domain.LeaveDO;
import com.alpha.query.DepartmentQuery;
import com.alpha.query.LeaveQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class LeaveManagerTest extends ITestBase {

    @Resource
    private LeaveManager leaveManager;

    @Test
    public void testInsert() {
        LeaveDO leaveDO = new LeaveDO();
        leaveDO.setDriverId(1);
        leaveDO.setDriverName("2");
        leaveDO.setMobilePhone("3");
//            leaveDO.setTeam(driverDO);
        leaveDO.setStartDate(CalendarUtil.formatDate(new Date(), CalendarUtil.TIME_PATTERN));
        leaveDO.setEndDate(CalendarUtil.formatDate(new Date(), CalendarUtil.TIME_PATTERN));
        leaveDO.setReason("4");
        leaveDO.setRemark("5");
        leaveDO.setFile("6");
        leaveDO.setStatus(SystemConstant.LEAVE_WAIT_FIRST_VERIFY);
        boolean res = leaveManager.insert(leaveDO);
        System.out.println("res=" + res);
    }

    @Test
    public void testQuery() {
        LeaveQuery leaveQuery = new LeaveQuery();
        leaveQuery.setPage(1);
        leaveQuery.setPageSize(10);
        List<LeaveDO> list = leaveManager.query(leaveQuery);
        System.out.println("res=" + JSON.toJSONString(list));
    }

    @Test
    public void testUpdate() {
        LeaveDO leaveDO = new LeaveDO();
        leaveDO.setId(1);
        leaveDO.setDriverName("21");
        boolean res = leaveManager.update(leaveDO);
        System.out.println("res=" + res);
    }
}
