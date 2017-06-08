package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.MaintainDO;
import com.alpha.domain.MaintainSumDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.MaintainManager;
import com.alpha.query.MaintainQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/5/27.
 */
public class MaintainStatistics extends BaseAjaxModule {

    @Resource
    private MaintainManager maintainManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("vehicleNO") String vehicleNO, @Param("team") String team) {
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
            pageSize = pageSize > 0 ? pageSize : 10;
            MaintainQuery maintainQuery = new MaintainQuery();
            maintainQuery.setPage(page);
            maintainQuery.setPageSize(pageSize);
            maintainQuery.setStartDate(startDate == null ? null : new Date(startDate));
            maintainQuery.setEndDate(endDate == null ? null : new Date(endDate));
            maintainQuery.setVehicleNO(vehicleNO);
            maintainQuery.setTeam(team);
            List<String> statusList = new ArrayList<String>();
            statusList.add(SystemConstant.MAINTAIN_VERIFY_PASS);
            maintainQuery.setStatusList(statusList);
            List<MaintainSumDO> list = maintainManager.queryGroupByVehicle(maintainQuery);
            int number = maintainManager.countGroupByVehicle(maintainQuery);
            PageResult<List<MaintainSumDO>> result = new PageResult<List<MaintainSumDO>>();
            result.setData(list);
            result.setPage(page);
            result.setPageSize(pageSize);
            result.setNumber(number);
            print(result);
        } catch (Exception e) {
            Result<MaintainDO> result = new Result<MaintainDO>();
            logger.error("MaintainStatistics execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }
}
