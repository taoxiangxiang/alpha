package com.alpha.timejob;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.YunUtil;
import com.alpha.domain.VehicleDO;
import com.alpha.domain.VehicleIllegalDO;
import com.alpha.manager.VehicleIllegalManager;
import com.alpha.manager.VehicleManager;
import com.alpha.query.VehicleIllegalQuery;
import com.alpha.query.VehicleQuery;
import org.omg.PortableInterceptor.INACTIVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by taoxiang on 2017/5/23.
 */
@Component("vehicleIllegalJob")
public class VehicleIllegalJob implements InitializingBean {

    private static final long LOCAL_CACHE_TIMER_START = 10 * 1000;
    private static final long LOCAL_CACHE_TIMER_PERIOD = 7 * 24 * 3600 * 1000;

    private static final Logger logger = LoggerFactory.getLogger(VehicleIllegalJob.class);

    @Resource
    private VehicleManager vehicleManager;
    @Resource
    private VehicleIllegalManager vehicleIllegalManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        Timer statTimer = new Timer();
        statTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                dealVehicleIllegalJob();
            }
        }, LOCAL_CACHE_TIMER_START, LOCAL_CACHE_TIMER_PERIOD);
    }

    private void dealVehicleIllegalJob() {
        try{
            VehicleQuery vehicleQuery = new VehicleQuery();
            for (int i = 1; i < 100000; i++) {
                vehicleQuery.setPage(i);
                vehicleQuery.setPageSize(100);
                List<VehicleDO> vehicleDOList = vehicleManager.query(vehicleQuery);
                if (vehicleDOList == null || vehicleDOList.size() == 0) {
                    break;
                }
                for (VehicleDO vehicleDO : vehicleDOList) {
                    dealSingleVehicle(vehicleDO);
                }
            }
        } catch (Exception e) {
            logger.error("VehicleIllegalJob dealVehicleIllegalJob catch exception", e);
        }
    }

    private void dealSingleVehicle(VehicleDO vehicleDO) {
        String diQu = "yangzhou";
        if ("皖".equals(vehicleDO.getVehicleNO().substring(0,1))) {
            diQu = "wuhu";
        }
        String result = YunUtil.queryVehicleIllegal(vehicleDO, diQu);
        if (result == null || result.length() == 0) {
            return;
        }
        JSONObject jsonObject = JSON.parseObject(result);
        String resultValue = String.valueOf(jsonObject.get("result"));
        if (resultValue == null || resultValue.length() == 0) {
            System.out.println(1);
            return;
        }
        JSONObject jsonObject1 = JSON.parseObject(resultValue);
        String listValue = String.valueOf(jsonObject1.get("list"));
        if (listValue == null || listValue.length() == 0) {
            System.out.println(2);
            return;
        }
        JSONArray jsonArray = JSON.parseArray(listValue);
        if (jsonArray == null || jsonArray.size() == 0) {
            System.out.println(3);
            return;
        }
        for (Object object : jsonArray) {
            JSONObject innerObject = JSON.parseObject(String.valueOf(object));
            String time = String.valueOf(innerObject.get("time"));
            String address = String.valueOf(innerObject.get("province")) + String.valueOf(innerObject.get("city") == null ? "" : innerObject.get("city"))
                    + String.valueOf(innerObject.get("town") == null ? "" : innerObject.get("town")) + String.valueOf(innerObject.get("address"));
            String content = String.valueOf(innerObject.get("content"));
            String illegalid = String.valueOf(innerObject.get("illegalid"));
            String price = String.valueOf(innerObject.get("price"));
            String score = String.valueOf(innerObject.get("score"));

            VehicleIllegalQuery vehicleIllegalQuery = new VehicleIllegalQuery();
            vehicleIllegalQuery.setVehicleNO(vehicleDO.getVehicleNO());
            vehicleIllegalQuery.setIllegalId(illegalid);
            List<VehicleIllegalDO> vehicleIllegalDOList = vehicleIllegalManager.query(vehicleIllegalQuery);
            if (vehicleIllegalDOList != null && vehicleIllegalDOList.size() > 0) {
                return;
            }
            VehicleIllegalDO vehicleIllegalDO = new VehicleIllegalDO();
            vehicleIllegalDO.setVehicleNO(vehicleDO.getVehicleNO());
            vehicleIllegalDO.setTeam(vehicleDO.getTeam());
            vehicleIllegalDO.setIllegalDate(CalendarUtil.toDate(time, CalendarUtil.TIME_PATTERN));
            vehicleIllegalDO.setReason(content);
            vehicleIllegalDO.setMoney(Double.valueOf(price));
            vehicleIllegalDO.setPoint(Integer.valueOf(score));
            vehicleIllegalDO.setIllegalAddress(address);
            vehicleIllegalDO.setIllegalId(illegalid);
            vehicleIllegalManager.insert(vehicleIllegalDO);
        }
    }
}
