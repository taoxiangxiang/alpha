package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.LeaveDO;
import com.alpha.domain.MaintainDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VerifyRecordDO;
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
                        @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                        @Param("status") String status, @Param("id") Integer id, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10;
            MaintainQuery maintainQuery = new MaintainQuery();
            if (id == null) {
                maintainQuery.setPage(page);
                maintainQuery.setPageSize(pageSize);
                maintainQuery.setStartDate(startDate);
                maintainQuery.setEndDate(endDate);
                if (status != null) {
                    List<String> statusList = new ArrayList<String>();
                    statusList.add(status);
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
        Map<Integer, VerifyRecordDO> map = new HashMap<Integer, VerifyRecordDO>();
        if (verifyRecordDOList != null) {
            for (VerifyRecordDO verifyRecordDO : verifyRecordDOList) {
                VerifyRecordDO insideDO = map.get(verifyRecordDO.getApplicationId());
                if (insideDO == null || insideDO.getGmtCreate().before(verifyRecordDO.getGmtCreate())) {
                    map.put(verifyRecordDO.getApplicationId(), verifyRecordDO);
                }
            }
        }
        for (MaintainDO maintainDO : maintainDOList) {
            maintainDO.setVerifyRecord(map.get(maintainDO.getId()));
            maintainDO.setStatus(SystemConstant.maintainStatusMap.get(maintainDO.getStatus()));
        }
    }
}
