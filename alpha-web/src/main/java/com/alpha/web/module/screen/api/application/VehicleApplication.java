package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VerifyRecordDO;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.manager.VerifyRecordManager;
import com.alpha.query.VehicleApplicationQuery;
import com.alpha.query.VerifyRecordQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import java.util.*;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/6.
 */
public class VehicleApplication extends BaseAjaxModule {

    @Resource
    private VehicleApplicationManager vehicleApplicationManager;
    @Resource
    private VerifyRecordManager verifyRecordManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("status") String status, @Param("id") Integer id, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 1000;
            SystemAccountDO systemAccountDO = getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请先登录系统"));
                return;
            }
            VehicleApplicationQuery vehicleApplicationQuery = new VehicleApplicationQuery();
            /*权限判断*/
            if (systemAccountDO.isDriver()) {
                print(new Result<String>("您暂无此项功能权限"));
                return;
            }
            if (!systemAccountDO.hasAuth()) {
                vehicleApplicationQuery.setApplicantId(systemAccountDO.getId());
            }
            if (id == null) {
                vehicleApplicationQuery.setPage(page);
                vehicleApplicationQuery.setPageSize(pageSize);
                vehicleApplicationQuery.setStartDate(startDate == null ? null : new Date(startDate));
                vehicleApplicationQuery.setEndDate(endDate == null ? null : new Date(endDate));
                if (status != null) {
                    List<String> statusList = new ArrayList<String>();
                    String[] splitStatusArray = status.split(",");
                    for (String splitStatus: splitStatusArray) {
                        statusList.add(splitStatus);
                    }
                    vehicleApplicationQuery.setStatusList(statusList);
                }

                List<VehicleApplicationDO> list = vehicleApplicationManager.query(vehicleApplicationQuery);
                addVerifyRecord(list);
                int number = vehicleApplicationManager.count(vehicleApplicationQuery);
                PageResult<List<VehicleApplicationDO>> result = new PageResult<List<VehicleApplicationDO>>();
                result.setData(list);
                result.setPage(page);
                result.setPageSize(pageSize);
                result.setNumber(number);
                print(result);
            } else {
                Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
                vehicleApplicationQuery.setId(id);
                List<VehicleApplicationDO> list = vehicleApplicationManager.query(vehicleApplicationQuery);
                addVerifyRecord(list);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关申请单数据：id=" + id);
                } else {
                    result.setData(list.get(0));
                }
                print(result);
            }
        } catch (Exception e) {
            Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
            logger.error("VehicleApplication execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }

    private void addVerifyRecord(List<VehicleApplicationDO> vehicleApplicationDOList) {
        if (vehicleApplicationDOList == null || vehicleApplicationDOList.size() == 0) {
            return;
        }
        List<Integer> applicationIdList = new ArrayList<Integer>();
        for (VehicleApplicationDO vehicleApplicationDO : vehicleApplicationDOList) {
            applicationIdList.add(vehicleApplicationDO.getId());
        }
        VerifyRecordQuery verifyRecordQuery = new VerifyRecordQuery();
        verifyRecordQuery.setApplicationEvent(SystemConstant.VERIFY_EVENT_VEHICLE_APPLICATION);
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
        for (VehicleApplicationDO vehicleApplicationDO : vehicleApplicationDOList) {
            vehicleApplicationDO.setVerifyRecordList(map.get(vehicleApplicationDO.getId()));
            vehicleApplicationDO.setStatus(SystemConstant.vehicleApplicationStatusMap.get(vehicleApplicationDO.getStatus()));
        }
    }
}
