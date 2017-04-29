package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.LeaveDO;
import com.alpha.manager.LeaveManager;
import com.alpha.query.LeaveQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/29.
 */
public class Leave extends BaseAjaxModule {

    @Resource
    private LeaveManager leaveManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                        @Param("status") String status,
                        @Param("id") Integer id, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10;
            LeaveQuery leaveQuery = new LeaveQuery();
            if (id == null) {
                PageResult<List<LeaveDO>> result = new PageResult<List<LeaveDO>>();
                leaveQuery.setPage(page);
                leaveQuery.setPageSize(pageSize);
                leaveQuery.setStartDate(startDate);
                leaveQuery.setEndDate(endDate);
                if (status != null) {
                    List<String> statusList = new ArrayList<String>();
                    statusList.add(status);
                    leaveQuery.setStatusList(statusList);
                }
                List<LeaveDO> list = leaveManager.query(leaveQuery);
                int number = leaveManager.count(leaveQuery);
                result.setPage(page);
                result.setPageSize(pageSize);
                result.setNumber(number);
                result.setData(list);
                print(result);
            } else {
                Result<LeaveDO> result = new Result<LeaveDO>();
                leaveQuery.setId(id);
                List<LeaveDO> list = leaveManager.query(leaveQuery);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关数据");
                } else {
                    result.setData(list.get(0));
                }
                print(result);
            }

        } catch (Exception e) {
            Result<LeaveDO> result = new Result<LeaveDO>();
            logger.error("Leave execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }
}
