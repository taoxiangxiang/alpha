package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleDO;
import com.alpha.domain.VehicleGasSumDO;
import com.alpha.domain.VehicleInfoDO;
import com.alpha.manager.VehicleGasManager;
import com.alpha.manager.VehicleManager;
import com.alpha.query.VehicleGasQuery;
import com.alpha.query.VehicleQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/5/6.
 */
public class VehicleInfoStatistics extends BaseAjaxModule {

    @Resource
    private VehicleManager vehicleManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("vehicleNO") String vehicleNO, @Param("team") String team) {
        PageResult<List<VehicleInfoDO>> result = new PageResult<List<VehicleInfoDO>>();
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
            VehicleQuery vehicleQuery = new VehicleQuery();
            vehicleQuery.setPage(page);
            vehicleQuery.setPageSize(pageSize);
            vehicleQuery.setVehicleNO(vehicleNO);
            vehicleQuery.setTeam(team);
            List<String> statusList = new ArrayList<String>();
            statusList.add(SystemConstant.VEHICLE_CAN_USE);
            statusList.add(SystemConstant.VEHICLE_OFF_LINE);
            statusList.add(SystemConstant.VEHICLE_USING);
            statusList.add(SystemConstant.VEHICLE_MAINTAIN);
            vehicleQuery.setStatusList(statusList);
            List<VehicleDO> vehicleDOList = vehicleManager.query(vehicleQuery);
            List<VehicleInfoDO> vehicleInfoDOList = vehicleManager.getVehicleInfo(vehicleDOList, vehicleNO, team, startDate, endDate);
            int number = vehicleManager.count(vehicleQuery);
            result.setPage(page);
            result.setPageSize(pageSize);
            result.setNumber(number);
            result.setData(vehicleInfoDOList);
            print(result);
        } catch (Exception e) {
            logger.error("VehicleUse execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }


}
