package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.DriverDO;
import com.alpha.domain.SystemAccountDO;
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
                        @Param("accidentDate") Long accidentDate, @Param(name="accidentDesc", defaultValue="") String accidentDesc,
                        @Param("accidentAddress") String accidentAddress, @Param("driverId") Integer driverId,
                        @Param("result") String result1, @Param(name="dealDesc", defaultValue="") String dealDesc,
                        @Param("liablePerson") String liablePerson, @Param("maintainAddress") String maintainAddress,
                        @Param("liableDesc") String liableDesc, @Param("money") Double money,
                        @Param(name="file", defaultValue="") String file, @Param(name="remark", defaultValue="") String remark,
                        @Param("id") Integer id, Context context) {
        Result<String> result = new Result<String>();
        try {
            file = (file == null ? "" : file);
            remark = (remark == null ? "" : remark);
            accidentDesc = (accidentDesc == null ? "" : accidentDesc);
            dealDesc = (dealDesc == null ? "" : dealDesc);
            SystemAccountDO systemAccountDO = this.getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!systemAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
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
            vehicleAccidentDO.setAccidentDate(accidentDate == null ? null : new Date(accidentDate));
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
            String checkParamRes = checkParam(vehicleAccidentDO);
            if (!"ok".equals(checkParamRes)) {
                result.setErrMsg(checkParamRes);
                print(result);
                return;
            }
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

    private String checkParam(VehicleAccidentDO vehicleAccidentDO) {
        if (StringUtil.isBlank(vehicleAccidentDO.getVehicleNO())) {
            return "请填写车牌号";
        }
        if (StringUtil.isBlank(vehicleAccidentDO.getTeam())) {
            return "请设置车牌对应的车队";
        }
        if (vehicleAccidentDO.getDriverId() == null) {
            return "请填写司机";
        }
        if (vehicleAccidentDO.getAccidentDate() == null) {
            return "请填写事故日期";
        }
        if (StringUtil.isBlank(vehicleAccidentDO.getAccidentAddress())) {
            return "请填写事故地点";
        }
        if (StringUtil.isBlank(vehicleAccidentDO.getResult())) {
            return "请填写处理结果";
        }
        if (StringUtil.isBlank(vehicleAccidentDO.getLiablePerson())) {
            return "请填写定损人";
        }
        if (StringUtil.isBlank(vehicleAccidentDO.getMaintainAddress())) {
            return "请填写维修地点";
        }
        if (StringUtil.isBlank(vehicleAccidentDO.getLiableDesc())) {
            return "请填写责任认定";
        }
        if (vehicleAccidentDO.getMoney() == null) {
            return "请填写保险赔偿金额";
        }
        return "ok";
    }
}
