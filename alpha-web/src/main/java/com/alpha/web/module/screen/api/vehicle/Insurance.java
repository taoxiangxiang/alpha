package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.InsuranceDO;
import com.alpha.manager.InsuranceManager;
import com.alpha.query.InsuranceQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
public class Insurance extends BaseAjaxModule {

    @Resource
    private InsuranceManager insuranceManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("vehicleNO") String vehicleNO,
                        @Param("id") Integer id, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10;
            InsuranceQuery insuranceQuery = new InsuranceQuery();
            if (id == null) {
                PageResult<List<InsuranceDO>> result = new PageResult<List<InsuranceDO>>();
                insuranceQuery.setPage(page);
                insuranceQuery.setPageSize(pageSize);
                insuranceQuery.setVehicleNO(vehicleNO);
                List<InsuranceDO> list = insuranceManager.query(insuranceQuery);
                int number = insuranceManager.count(insuranceQuery);
                result.setData(list);
                result.setPage(page);
                result.setPageSize(pageSize);
                result.setNumber(number);
                result.setData(list);
                print(result);
            } else {
                Result<InsuranceDO> result = new Result<InsuranceDO>();
                insuranceQuery.setId(id);
                List<InsuranceDO> list = insuranceManager.query(insuranceQuery);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关数据");
                } else {
                    result.setData(list.get(0));
                }
                print(result);
            }

        } catch (Exception e) {
            Result<InsuranceDO> result = new Result<InsuranceDO>();
            logger.error("Insurance execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }
}
