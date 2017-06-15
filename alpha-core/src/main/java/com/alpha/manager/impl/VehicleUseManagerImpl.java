package com.alpha.manager.impl;

import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.constans.YunUtil;
import com.alpha.dao.VehicleUseDao;
import com.alpha.domain.*;
import com.alpha.manager.DriverManager;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.manager.VehicleManager;
import com.alpha.manager.VehicleUseManager;
import com.alpha.query.DriverQuery;
import com.alpha.query.VehicleApplicationQuery;
import com.alpha.query.VehicleUseQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taoxiang on 2017/4/16.
 */
@Component("vehicleUseManager")
public class VehicleUseManagerImpl implements VehicleUseManager {

    @Resource
    private VehicleUseDao vehicleUseDao;
    @Resource
    private VehicleApplicationManager vehicleApplicationManager;
    @Resource
    private DriverManager driverManager;
    @Resource
    private VehicleManager vehicleManager;

    @Override
    @Transactional(rollbackForClassName="Exception")
    public String batchInsert(VehicleApplicationDO vehicleApplicationDO, List<VehicleUseDO> vehicleUseDOList) throws Exception {
        if (SystemConstant.VEHICLE_VERIFY_PASS.equals(vehicleApplicationDO.getStatus())) {
            vehicleApplicationDO.setStatus(SystemConstant.VEHICLE_ALREADY_SCHEDULE);
            if (!vehicleApplicationManager.update(vehicleApplicationDO)) {
                return "车辆调派失败，请稍后重试";
            }
        }
        for (VehicleUseDO vehicleUseDO : vehicleUseDOList) {
            DriverDO driverDO = new DriverDO();
            driverDO.setId(vehicleUseDO.getDriverId());
            driverDO.setStatus(SystemConstant.DRIVER_USING);
            driverDO.setApplicationId(vehicleUseDO.getApplicationId());

            VehicleDO vehicleDO = new VehicleDO();
            vehicleDO.setId(vehicleUseDO.getVehicleId());
            vehicleDO.setStatus(SystemConstant.VEHICLE_USING);
            vehicleDO.setApplicationId(vehicleUseDO.getApplicationId());

            if (!insert(vehicleUseDO) || !driverManager.update(driverDO) || !vehicleManager.update(vehicleDO)) {
                throw new Exception("数据库写入失败");
            }
            YunUtil.sendVehicleVerifyPass(vehicleUseDO);
        }
        return "true";
    }

    @Override
    public boolean insert(VehicleUseDO vehicleUseDO) {
        return vehicleUseDao.insert(vehicleUseDO);
    }

    @Override
    public List<VehicleUseDO> query(VehicleUseQuery vehicleUseQuery) {
        List<VehicleUseDO> vehicleUseDOList = vehicleUseDao.query(vehicleUseQuery);
        addApplication(vehicleUseDOList);
        addDriver(vehicleUseDOList);
        return vehicleUseDOList;
    }

    @Override
    public VehicleUseDO queryById(int id) {
        VehicleUseQuery vehicleUseQuery = new VehicleUseQuery();
        vehicleUseQuery.setId(id);
        List<VehicleUseDO> vehicleUseDOList = vehicleUseDao.query(vehicleUseQuery);
        if (vehicleUseDOList == null || vehicleUseDOList.size() == 0) {
            return null;
        } else {
            return vehicleUseDOList.get(0);
        }
    }

    @Override
    public int count(VehicleUseQuery vehicleUseQuery) {
        return vehicleUseDao.count(vehicleUseQuery);
    }

    @Override
    @Transactional(rollbackForClassName="Exception")
    public boolean update(VehicleUseDO vehicleUseDO) throws Exception {
        if (!vehicleUseDao.update(vehicleUseDO)) {
            return false;
        }
        VehicleUseDO vehicleUseDOInDB = queryById(vehicleUseDO.getId());
        VehicleDO vehicleDO = vehicleManager.queryByVehicleNO(vehicleUseDOInDB.getVehicleNO());
        if (vehicleUseDOInDB.getEndMile() > vehicleDO.getMile()) {
            vehicleDO.setMile(vehicleUseDOInDB.getEndMile());
            if (!vehicleManager.update(vehicleDO)) {
                throw new Exception("数据库写入失败");
            }
        }
        return true;
    }

    @Override
    public List<VehicleUseSumDO> queryGroupByDriver(VehicleUseQuery vehicleUseQuery) {
        List<VehicleUseSumDO> list =  vehicleUseDao.queryGroupByDriver(vehicleUseQuery);
        if (list == null || list.size() == 0) return list;
        for (VehicleUseSumDO vehicleUseSumDO : list) {
            Double allFee = getValue(vehicleUseSumDO.getCailvFee()) + getValue(vehicleUseSumDO.getFuwuFee())
                    + getValue(vehicleUseSumDO.getJiabanFee()) + getValue(vehicleUseSumDO.getGuoluFee())
                    + getValue(vehicleUseSumDO.getGuoqiaoFee()) + getValue(vehicleUseSumDO.getTingcheFee())
                    + getValue(vehicleUseSumDO.getXicheFee()) + getValue(vehicleUseSumDO.getOtherFee());
            vehicleUseSumDO.setAllFee(allFee);
            if (vehicleUseSumDO.getUseTime() == null) {
                continue;
            }
            int useTime = vehicleUseSumDO.getUseTime();
            int day = useTime/(24*3600);
            int hour = (useTime - day * 24 * 3600)/3600;
            int minutes = (useTime - day * 24 * 3600 - hour * 3600)/60;
            String useTimeDesc = (day > 0 ? day + "天" : "") + (hour > 0 ? hour + "小时" : "") + (minutes > 0 ? minutes + "分钟" : "");
            vehicleUseSumDO.setUseTimeDesc(useTimeDesc);
        }
        addDriver2(list);
        return list;
    }

    private double getValue(Double value) {
        return value == null ? 0 : value;
    }

    @Override
    public int countGroupByDriver(VehicleUseQuery vehicleUseQuery) {
        return vehicleUseDao.countGroupByDriver(vehicleUseQuery);
    }

    private void addApplication(List<VehicleUseDO> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        List<Integer> applicationIdList = new ArrayList<Integer>();
        for (VehicleUseDO vehicleUseDO : list) {
            applicationIdList.add(vehicleUseDO.getApplicationId());
        }
        VehicleApplicationQuery vehicleApplicationQuery = new VehicleApplicationQuery();
        vehicleApplicationQuery.setIdList(applicationIdList);
        List<VehicleApplicationDO> vehicleApplicationDOList = vehicleApplicationManager.query(vehicleApplicationQuery);
        Map<Integer, VehicleApplicationDO> map = new HashMap<Integer, VehicleApplicationDO>();
        if (vehicleApplicationDOList == null || vehicleApplicationDOList.size() == 0) {
            return;
        }
        for (VehicleApplicationDO vehicleApplicationDO : vehicleApplicationDOList) {

            map.put(vehicleApplicationDO.getId(), vehicleApplicationDO);
        }
        for (VehicleUseDO vehicleUseDO : list) {
            VehicleApplicationDO vehicleApplicationDO = map.get(vehicleUseDO.getApplicationId());
            vehicleUseDO.setVehicleApplicationDO(vehicleApplicationDO);
            vehicleUseDO.setApplicationNO(vehicleApplicationDO.getApplicationNO());
            vehicleUseDO.setAlreadyCheck(vehicleUseDO.getActualEndDate() != null);
        }
    }

    private void addDriver(List<VehicleUseDO> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        List<Integer> driverIdList = new ArrayList<Integer>();
        for (VehicleUseDO vehicleUseDO : list) {
            driverIdList.add(vehicleUseDO.getDriverId());
        }
        DriverQuery driverQuery = new DriverQuery();
        driverQuery.setIdList(driverIdList);
        List<DriverDO> driverDOList = driverManager.query(driverQuery);
        Map<Integer, DriverDO> map = new HashMap<Integer, DriverDO>();
        if (driverDOList == null || driverDOList.size() == 0) {
            return;
        }
        for (DriverDO driverDO : driverDOList) {

            map.put(driverDO.getId(), driverDO);
        }
        for (VehicleUseDO vehicleUseDO : list) {
            DriverDO driverDO = map.get(vehicleUseDO.getDriverId());
            vehicleUseDO.setDriverDO(driverDO);
        }
    }

    private void addDriver2(List<VehicleUseSumDO> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        List<Integer> driverIdList = new ArrayList<Integer>();
        for (VehicleUseSumDO vehicleUseSumDO : list) {
            driverIdList.add(vehicleUseSumDO.getDriverId());
        }
        DriverQuery driverQuery = new DriverQuery();
        driverQuery.setIdList(driverIdList);
        List<DriverDO> driverDOList = driverManager.query(driverQuery);
        Map<Integer, DriverDO> map = new HashMap<Integer, DriverDO>();
        if (driverDOList == null || driverDOList.size() == 0) {
            return;
        }
        for (DriverDO driverDO : driverDOList) {

            map.put(driverDO.getId(), driverDO);
        }
        for (VehicleUseSumDO vehicleUseSumDO : list) {
            DriverDO driverDO = map.get(vehicleUseSumDO.getDriverId());
            vehicleUseSumDO.setDriverDO(driverDO);
        }
    }
}
