package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverBindDO;
import com.alpha.domain.DriverDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.DriverBindManager;
import com.alpha.manager.DriverManager;
import com.alpha.manager.VehicleManager;
import com.alpha.query.DriverBindQuery;
import com.alpha.query.DriverQuery;
import com.alpha.query.VehicleQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class DriverBindAdd extends BaseAjaxModule {

    @Resource
    private DriverBindManager driverBindManager;
    @Resource
    private DriverManager driverManager;
    @Resource
    private VehicleManager vehicleManager;

    public void execute(@Param("vehicleId") int vehicleId, @Param("vehicleNO") String vehicleNO,
                        @Param("driverId") int driverId, @Param("driverName") String driverName,
                        @Param("team") String team, Context context) {
        Result<String> result = new Result<String>();
        try {
            SystemAccountDO systemAccountDO = this.getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!systemAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            if (vehicleId <= 0 || StringUtil.isBlank(vehicleNO)) {
                print(new Result<String>("请填写正确车牌号"));
                return;
            }
            if (driverId <= 0 || StringUtil.isBlank(driverName)) {
                print(new Result<String>("请填写正确司机"));
                return;
            }
            if (isDriverAlreadyBind(driverId)) {
                result.setErrMsg("司机" + driverName + "已经绑定了其他车辆，请勿重复绑定");
                print(result);
                return;
            }

            if (isVehicleAlreadyBind(vehicleId)) {
                result.setErrMsg("车辆" + vehicleNO + "已经绑定了其他司机，请勿重复绑定");
                print(result);
                return;
            }

            DriverQuery driverQuery = new DriverQuery();
            driverQuery.setId(driverId);
            List<DriverDO> driverDOList = driverManager.query(driverQuery);
            if (driverDOList == null || driverDOList.size() == 0) {
                result.setErrMsg("司机" + driverName + "不存在于系统中");
                print(result);
                return;
            }

            VehicleQuery vehicleQuery = new VehicleQuery();
            vehicleQuery.setId(vehicleId);
            List<VehicleDO>  vehicleDOList = vehicleManager.query(vehicleQuery);
            if (vehicleDOList == null || vehicleDOList.size() == 0) {
                result.setErrMsg("车辆" + vehicleNO + "不存在于系统中");
                print(result);
                return;
            }
            String driverTeam = driverDOList.get(0).getTeam();
            String vehicleTeam = vehicleDOList.get(0).getTeam();
            if (driverTeam == null || vehicleTeam == null || !vehicleTeam.equals(driverTeam)) {
                result.setErrMsg("车辆" + vehicleNO + "与" + "司机" + driverName + "不在同一个车队，请先更新车辆、司机车队信息");
                print(result);
                return;
            }

            DriverBindDO driverBindDO = new DriverBindDO();
            driverBindDO.setVehicleId(vehicleId);
            driverBindDO.setVehicleNO(vehicleNO);
            driverBindDO.setDriverId(driverId);
            driverBindDO.setDriverName(driverName);
            driverBindDO.setTeam(team);
            driverBindDO.setBindDate(new Date());
            driverBindDO.setStatus(SystemConstant.DRIVER_BIND_VEHICLE);
            boolean res = driverBindManager.insert(driverBindDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("DriverBindAdd execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
        }
        print(result);
    }

    private boolean isDriverAlreadyBind(int driverId) {
        DriverBindQuery driverBindQuery = new DriverBindQuery();
        driverBindQuery.setStatus(SystemConstant.DRIVER_BIND_VEHICLE);
        driverBindQuery.setDriverId(driverId);
        List<DriverBindDO> list = driverBindManager.query(driverBindQuery);
        return list != null && list.size() > 0;
    }

    private boolean isVehicleAlreadyBind(int vehicleId) {
        DriverBindQuery driverBindQuery = new DriverBindQuery();
        driverBindQuery.setStatus(SystemConstant.DRIVER_BIND_VEHICLE);
        driverBindQuery.setVehicleId(vehicleId);
        List<DriverBindDO> list = driverBindManager.query(driverBindQuery);
        return list != null && list.size() > 0;
    }
}
