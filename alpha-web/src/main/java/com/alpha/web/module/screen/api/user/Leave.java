package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.LeaveDO;
import com.alpha.domain.VerifyRecordDO;
import com.alpha.manager.LeaveManager;
import com.alpha.manager.VerifyRecordManager;
import com.alpha.query.LeaveQuery;
import com.alpha.query.VerifyRecordQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by taoxiang on 2017/4/29.
 */
public class Leave extends BaseAjaxModule {

    @Resource
    private LeaveManager leaveManager;
    @Resource
    private VerifyRecordManager verifyRecordManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
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
                leaveQuery.setStartDate(startDate == null ? null : new Date(startDate));
                leaveQuery.setEndDate(endDate == null ? null : new Date(endDate));
                if (status != null) {
                    List<String> statusList = new ArrayList<String>();
                    statusList.add(status);
                    leaveQuery.setStatusList(statusList);
                }
                List<LeaveDO> list = leaveManager.query(leaveQuery);
                addVerifyRecord(list);
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
                addVerifyRecord(list);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关数据");
                } else {
                    LeaveDO leaveDO = list.get(0);
                    result.setData(leaveDO);
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

    private void addVerifyRecord(List<LeaveDO> leaveDOList) {
        if (leaveDOList == null || leaveDOList.size() == 0) {
            return;
        }
        List<Integer> applicationIdList = new ArrayList<Integer>();
        for (LeaveDO leaveDO : leaveDOList) {
            applicationIdList.add(leaveDO.getId());
        }
        VerifyRecordQuery verifyRecordQuery = new VerifyRecordQuery();
        verifyRecordQuery.setApplicationEvent(SystemConstant.VERIFY_EVENT_LEAVE_APPLICATION);
        verifyRecordQuery.setPageSize(applicationIdList.size() * 5);
        verifyRecordQuery.setApplicationIdList(applicationIdList);
        List<VerifyRecordDO> verifyRecordDOList = verifyRecordManager.query(verifyRecordQuery);
        Map<Integer, List<VerifyRecordDO>> map = new HashMap<Integer, List<VerifyRecordDO>>();
        if (verifyRecordDOList != null) {
            for (VerifyRecordDO verifyRecordDO : verifyRecordDOList) {
                if (map.get(verifyRecordDO.getApplicationId()) == null) {
                    map.put(verifyRecordDO.getApplicationId(), new ArrayList<VerifyRecordDO>());
                }
                map.get(verifyRecordDO.getApplicationId()).add(verifyRecordDO);
            }
        }
        for (LeaveDO leaveDO : leaveDOList) {
            leaveDO.setVerifyRecordList(map.get(leaveDO.getId()));
            leaveDO.setStatus(SystemConstant.leaveStatusMap.get(leaveDO.getStatus()));
        }
    }
}
