package com.alpha.web.module.screen.api.notice;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.DriverDO;
import com.alpha.domain.RemindDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.DriverManager;
import com.alpha.manager.VehicleManager;
import com.alpha.query.DriverQuery;
import com.alpha.query.VehicleQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/6/13.
 */
public class Remind extends BaseAjaxModule {

    @Resource
    private VehicleManager vehicleManager;
    @Resource
    private DriverManager driverManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("team") String team, @Param("eventTitle") String eventTitle,  Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 1000;
            SystemAccountDO systemAccountDO = this.getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!systemAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            List<RemindDO> remindDOList = new ArrayList<RemindDO>();
            VehicleQuery vehicleQuery = new VehicleQuery();
            vehicleQuery.setPage(1);
            vehicleQuery.setPageSize(10000);
            List<VehicleDO> vehicleDOList = vehicleManager.query(vehicleQuery);
            Date oneMonthAfter = CalendarUtil.addDate(new Date(), 30);
            Date twoWeekAfter = CalendarUtil.addDate(new Date(), 14);
            if (vehicleDOList != null) {
                for (VehicleDO vehicleDO : vehicleDOList) {
                    if (oneMonthAfter.after(vehicleDO.getInsuranceDate())) {
                        RemindDO remindDO = new RemindDO();
                        remindDO.setEventTitle("保险到期");
                        remindDO.setTeam(vehicleDO.getTeam());
                        remindDO.setEventTarget("车牌：" + vehicleDO.getVehicleNO());
                        remindDO.setExpireContent("保险到期时间：" + CalendarUtil.toString(vehicleDO.getInsuranceDate(), CalendarUtil.TIME_PATTERN));
                        long day =  (vehicleDO.getInsuranceDate().getTime() - new Date().getTime())/24/3600/1000;
                        remindDO.setRemindInfo(day > 0 ? "剩余" + day + "天" : "已过期");
                        remindDOList.add(remindDO);
                    }
                    if (oneMonthAfter.after(vehicleDO.getCheckDate())) {
                        RemindDO remindDO = new RemindDO();
                        remindDO.setEventTitle("年检到期");
                        remindDO.setTeam(vehicleDO.getTeam());
                        remindDO.setEventTarget("车牌：" + vehicleDO.getVehicleNO());
                        remindDO.setExpireContent("年检到期时间：" + CalendarUtil.toString(vehicleDO.getCheckDate(), CalendarUtil.TIME_PATTERN));
                        long day =  (vehicleDO.getCheckDate().getTime() - new Date().getTime())/24/3600/1000;
                        remindDO.setRemindInfo(day > 0 ? "剩余" + day + "天" : "已过期");
                        remindDOList.add(remindDO);
                    }

                    if (twoWeekAfter.after(vehicleDO.getMaintainDate()) || (vehicleDO.getMile() != null && vehicleDO.getMaintainMile() != null &&
                            (vehicleDO.getMile() + 500) >= vehicleDO.getMaintainMile())) {
                        RemindDO remindDO = new RemindDO();
                        remindDO.setEventTitle("保养到期");
                        remindDO.setTeam(vehicleDO.getTeam());
                        remindDO.setEventTarget("车牌：" + vehicleDO.getVehicleNO());
                        remindDO.setExpireContent("保养到期时间：" + CalendarUtil.toString(vehicleDO.getMaintainDate(), CalendarUtil.TIME_PATTERN)
                                + "；到期里程：" + vehicleDO.getMaintainMile() + "公里");
                        long day =  (vehicleDO.getMaintainDate().getTime() - new Date().getTime())/24/3600/1000;
                        String remindInfo = (day > 0 ? "剩余" + day + "天，" : "时间已过期；");
                        if (vehicleDO.getMile() != null && vehicleDO.getMaintainMile() != null) {
                            int mile =  vehicleDO.getMaintainMile() - vehicleDO.getMile();
                            remindInfo = remindInfo + (mile > 0 ? "剩余里程：" + mile + "公里" : "目前里程" + vehicleDO.getMile() +"，里程已超出");
                        }
                        remindDO.setRemindInfo(remindInfo);
                        remindDOList.add(remindDO);
                    }
                }
            }
            DriverQuery driverQuery = new DriverQuery();
            driverQuery.setPage(1);
            driverQuery.setPageSize(10000);
            List<DriverDO> driverDOList = driverManager.query(driverQuery);
            if (driverDOList != null) {
                for (DriverDO driverDO : driverDOList) {
                    if (oneMonthAfter.after(driverDO.getLicenseEnd())) {
                        RemindDO remindDO = new RemindDO();
                        remindDO.setEventTitle("驾照到期");
                        remindDO.setTeam(driverDO.getTeam());
                        remindDO.setEventTarget("司机：" + driverDO.getName());
                        remindDO.setExpireContent("驾照到期时间：" + CalendarUtil.toString(driverDO.getLicenseEnd(), CalendarUtil.TIME_PATTERN));
                        long day =  (driverDO.getLicenseEnd().getTime() - new Date().getTime())/24/3600/1000;
                        remindDO.setRemindInfo(day > 0 ? "剩余" + day + "天" : "已过期");
                        remindDOList.add(remindDO);
                    }
                }
            }
            PageResult<List<RemindDO>> result = new PageResult<List<RemindDO>>();
            result.setPage(page);
            result.setPageSize(pageSize);
            List<RemindDO> filterRemindDOList = new ArrayList<RemindDO>();
            for (RemindDO remindDO : remindDOList) {
                if (!StringUtil.isBlank(team) && !remindDO.getTeam().contains(team)) {
                    continue;
                }
                if (!StringUtil.isBlank(eventTitle) && !remindDO.getEventTitle().contains(eventTitle)) {
                    continue;
                }
                filterRemindDOList.add(remindDO);
            }
            int number = filterRemindDOList.size();
            result.setNumber(number);
            int end = number < (page * pageSize) ? number : (page * pageSize);
            result.setData(filterRemindDOList.subList((page-1) * pageSize, end));
            print(result);
        } catch (Exception e) {
            Result<VehicleDO> result = new Result<VehicleDO>();
            logger.error("Remind execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }
}
