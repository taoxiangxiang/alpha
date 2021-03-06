package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.*;
import com.alpha.manager.MaintainManager;
import com.alpha.manager.VerifyRecordManager;
import com.alpha.query.MaintainQuery;
import com.alpha.query.VerifyRecordQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by taoxiang on 2017/4/26.
 */
public class Maintain extends BaseAjaxModule {

    @Resource
    private MaintainManager maintainManager;
    @Resource
    private VerifyRecordManager verifyRecordManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("status") String status, @Param("id") Integer id, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 1000;
            SystemAccountDO systemAccountDO = this.getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (systemAccountDO.hasNOAuth()) {
                print(new Result<String>("您没有维保申请功能权限"));
                return;
            }
            MaintainQuery maintainQuery = new MaintainQuery();
            if (systemAccountDO.isDriver()) {
                maintainQuery.setApplicantId(systemAccountDO.getId());
            }
            if (id == null) {
                maintainQuery.setPage(page);
                maintainQuery.setPageSize(pageSize);
                maintainQuery.setStartDate(startDate == null ? null : new Date(startDate));
                maintainQuery.setEndDate(endDate == null ? null : new Date(endDate));
                if (status != null) {
                    List<String> statusList = new ArrayList<String>();
                    String[] splitStatusArray = status.split(",");
                    for (String splitStatus: splitStatusArray) {
                        statusList.add(splitStatus);
                    }
                    maintainQuery.setStatusList(statusList);
                }
                List<MaintainDO> list = maintainManager.query(maintainQuery);
                addVerifyRecord(list);
                int number = maintainManager.count(maintainQuery);
                PageResult<List<MaintainDO>> result = new PageResult<List<MaintainDO>>();
                result.setData(list);
                result.setPage(page);
                result.setPageSize(pageSize);
                result.setNumber(number);
                print(result);
            } else {
                Result<MaintainDO> result = new Result<MaintainDO>();
                maintainQuery.setId(id);
                List<MaintainDO> list = maintainManager.query(maintainQuery);
                addVerifyRecord(list);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关申请单数据：id=" + id);
                } else {
                    result.setData(list.get(0));
                }
                print(result);
            }
        } catch (Exception e) {
            Result<MaintainDO> result = new Result<MaintainDO>();
            logger.error("Maintain execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }

    private void addVerifyRecord(List<MaintainDO> maintainDOList) {
        if (maintainDOList == null || maintainDOList.size() == 0) {
            return;
        }
        List<Integer> applicationIdList = new ArrayList<Integer>();
        for (MaintainDO maintainDO : maintainDOList) {
            applicationIdList.add(maintainDO.getId());
        }
        VerifyRecordQuery verifyRecordQuery = new VerifyRecordQuery();
        verifyRecordQuery.setApplicationEvent(SystemConstant.VERIFY_EVENT_MAINTAIN_APPLICATION);
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
        for (MaintainDO maintainDO : maintainDOList) {
            maintainDO.setVerifyRecordList(map.get(maintainDO.getId()));
            maintainDO.setStatus(SystemConstant.maintainStatusMap.get(maintainDO.getStatus()));
        }
    }
}
