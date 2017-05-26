package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.DriverDO;
import com.alpha.domain.VehicleGasDO;
import com.alpha.domain.VehicleIllegalDO;
import com.alpha.manager.DriverManager;
import com.alpha.manager.VehicleGasManager;
import com.alpha.manager.VehicleIllegalManager;
import com.alpha.query.DriverQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import com.alpha.web.module.screen.api.user.Driver;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
public class VehicleIllegalAdd extends BaseAjaxModule {

    @Resource
    private VehicleIllegalManager vehicleIllegalManager;
    @Resource
    private DriverManager driverManager;

    public void execute(@Param("vehicleNO") String vehicleNO, @Param("team") String team,
                        @Param("illegalDate") Long illegalDate, @Param("reason") String reason,
                        @Param("illegalAddress") String illegalAddress, @Param("driverId") Integer driverId,
                        @Param("money") Double money, @Param("point") Integer point,
                        @Param("file") String file, @Param("remark") String remark, Context context) {
        Result<String> result = new Result<String>();
        try {
            DriverDO driverDO = null;
            if (driverId != null && driverId > 0) {
                DriverQuery driverQuery = new DriverQuery();
                driverQuery.setId(driverId);
                List<DriverDO> driverDOList = driverManager.query(driverQuery);
                if (driverDOList != null && driverDOList.size() > 0) {
                    driverDO = driverDOList.get(0);
                }
            }
            if (driverDO == null) {
                result.setErrMsg("不存在该司机");
                print(result);
                return;
            }
            VehicleIllegalDO vehicleIllegalDO = new VehicleIllegalDO();
            vehicleIllegalDO.setVehicleNO(vehicleNO);
            vehicleIllegalDO.setTeam(team);
            vehicleIllegalDO.setIllegalDate(illegalDate == null ? null : CalendarUtil.formatDate(new Date(illegalDate), CalendarUtil.TIME_PATTERN));
            vehicleIllegalDO.setReason(reason);
            vehicleIllegalDO.setIllegalAddress(illegalAddress);
            vehicleIllegalDO.setDriverId(driverId);
            vehicleIllegalDO.setDriverName(driverDO.getName());
            vehicleIllegalDO.setMoney(money);
            vehicleIllegalDO.setPoint(point);
            vehicleIllegalDO.setFile(file);
            vehicleIllegalDO.setRemark(remark);
            boolean res = vehicleIllegalManager.insert(vehicleIllegalDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("InsuranceAdd execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
        }
        print(result);
    }
}
