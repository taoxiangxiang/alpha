package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleGasDO;
import com.alpha.domain.VehicleGasSumDO;
import com.alpha.domain.VehicleUseSumDO;
import com.alpha.manager.VehicleGasManager;
import com.alpha.manager.VehicleUseManager;
import com.alpha.query.VehicleGasQuery;
import com.alpha.query.VehicleUseQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/5/6.
 */
public class VehicleGasStatistics extends BaseAjaxModule {

    @Resource
    private VehicleGasManager vehicleGasManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("vehicleNO") String vehicleNO, @Param("team") String team) {
        PageResult<List<VehicleGasSumDO>> result = new PageResult<List<VehicleGasSumDO>>();
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
            pageSize = pageSize > 0 ? pageSize : 1000;
            VehicleGasQuery vehicleGasQuery = new VehicleGasQuery();
            vehicleGasQuery.setPage(page);
            vehicleGasQuery.setPageSize(pageSize);
            vehicleGasQuery.setStartDate(startDate == null ? null : CalendarUtil.formatDate(new Date(startDate), CalendarUtil.TIME_PATTERN));
            vehicleGasQuery.setEndDate(endDate == null ? null : CalendarUtil.formatDate(new Date(endDate), CalendarUtil.TIME_PATTERN));
            vehicleGasQuery.setVehicleNO(vehicleNO);
            vehicleGasQuery.setTeam(team);
            List<VehicleGasSumDO> list = vehicleGasManager.queryGroupByVehicle(vehicleGasQuery);
            int number = vehicleGasManager.countGroupByVehicle(vehicleGasQuery);
            result.setData(list);
            result.setPage(page);
            result.setPageSize(pageSize);
            result.setNumber(number);
            result.setData(list);
            print(result);
        } catch (Exception e) {
            logger.error("VehicleUse execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }
}
