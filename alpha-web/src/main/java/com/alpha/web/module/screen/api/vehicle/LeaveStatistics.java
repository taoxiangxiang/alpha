package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverDO;
import com.alpha.domain.LeaveDO;
import com.alpha.domain.LeaveSumDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.DriverManager;
import com.alpha.manager.LeaveManager;
import com.alpha.query.DriverQuery;
import com.alpha.query.LeaveQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by taoxiang on 2017/5/27.
 */
public class LeaveStatistics extends BaseAjaxModule {

    @Resource
    private LeaveManager leaveManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("driverName") String driverName, @Param("team") String team) {

        try {
            SystemAccountDO curAccountDO = this.getAccount();
            if (curAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!curAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10000;
            LeaveQuery leaveQuery = new LeaveQuery();
            PageResult<List<LeaveSumDO>> result = new PageResult<List<LeaveSumDO>>();
            leaveQuery.setPage(page);
            leaveQuery.setPageSize(pageSize);
            leaveQuery.setDriverName(driverName);
            leaveQuery.setTeam(team);
            List<String> statusList = new ArrayList<String>();
            statusList.add(SystemConstant.LEAVE_VERIFY_PASS);
            leaveQuery.setStatusList(statusList);
            List<LeaveSumDO> leaveSumDOList = leaveManager.getSumList(leaveManager.query(leaveQuery), startDate == null ? null : new Date(startDate), endDate == null ? null : new Date(endDate));
            int number = leaveSumDOList.size();
            result.setPage(page);
            result.setPageSize(pageSize);
            result.setNumber(number);
            int end = number < ((page) * pageSize - 1) ? number : ((page) * pageSize - 1);
            result.setData(leaveSumDOList.subList((page-1) * pageSize, end));
            print(result);
        } catch (Exception e) {
            Result<LeaveDO> result = new Result<LeaveDO>();
            logger.error("LeaveStatistics execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }


}
