package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.DriverDO;
import com.alpha.domain.VehicleAccidentDO;
import com.alpha.domain.VehicleIllegalDO;
import com.alpha.manager.DriverManager;
import com.alpha.manager.VehicleAccidentManager;
import com.alpha.manager.VehicleIllegalManager;
import com.alpha.query.DriverQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
public class VehicleAccidentUpdate extends BaseAjaxModule {

    @Resource
    private VehicleAccidentManager vehicleAccidentManager;
    @Resource
    private DriverManager driverManager;

    public void execute(@Param("vehicleNO") String vehicleNO, @Param("team") String team,
                        @Param("accidentDate") Date accidentDate, @Param("accidentDesc") String accidentDesc,
                        @Param("accidentAddress") String accidentAddress, @Param("driverId") Integer driverId,
                        @Param("result") String result1, @Param("dealDesc") String dealDesc,
                        @Param("liablePerson") String liablePerson, @Param("maintainAddress") String maintainAddress,
                        @Param("liableDesc") String liableDesc, @Param("money") Double money,
                        @Param("file") String file, @Param("remark") String remark,
                        @Param("id") Integer id, Context context) {
        Result<String> result = new Result<String>();
        try {
            DriverDO driverDO = null;
            if (driverId != null && driverId > 0) {
                DriverQuery driverQuery = new DriverQuery();
                driverQuery.setId(driverId);
                List<DriverDO> driverDOList = driverManager.query(driverQuery);
                if (driverDOList == null || driverDOList.size() == 0) {
                    result.setErrMsg("不存在该司机");
                    print(result);
                    return;
                }
                driverDO = driverDOList.get(0);
            }
            VehicleAccidentDO vehicleAccidentDO = new VehicleAccidentDO();
            vehicleAccidentDO.setId(id);
            vehicleAccidentDO.setVehicleNO(vehicleNO);
            vehicleAccidentDO.setTeam(team);
            vehicleAccidentDO.setAccidentDate(CalendarUtil.formatDate(accidentDate, CalendarUtil.TIME_PATTERN));
            vehicleAccidentDO.setAccidentDesc(accidentDesc);
            vehicleAccidentDO.setAccidentAddress(accidentAddress);
            vehicleAccidentDO.setDriverId(driverId);
            vehicleAccidentDO.setDriverName(driverDO == null ? null : driverDO.getName());
            vehicleAccidentDO.setMoney(money);
            vehicleAccidentDO.setResult(result1);
            vehicleAccidentDO.setDealDesc(dealDesc);
            vehicleAccidentDO.setLiableDesc(liableDesc);
            vehicleAccidentDO.setLiablePerson(liablePerson);
            vehicleAccidentDO.setMaintainAddress(maintainAddress);
            vehicleAccidentDO.setFile(file);
            vehicleAccidentDO.setRemark(remark);
            boolean res = vehicleAccidentManager.update(vehicleAccidentDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("VehicleIllegalUpdate execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
        }
        print(result);
    }
}