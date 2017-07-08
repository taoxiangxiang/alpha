package com.alpha.manager.impl;

import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.dao.VehicleDao;
import com.alpha.domain.*;
import com.alpha.manager.MaintainManager;
import com.alpha.manager.VehicleGasManager;
import com.alpha.manager.VehicleIllegalManager;
import com.alpha.manager.VehicleManager;
import com.alpha.query.MaintainQuery;
import com.alpha.query.VehicleGasQuery;
import com.alpha.query.VehicleIllegalQuery;
import com.alpha.query.VehicleQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("vehicleManager")
public class VehicleManagerImpl implements VehicleManager {

    @Resource
    private VehicleDao vehicleDao;
    @Resource
    private VehicleGasManager vehicleGasManager;
    @Resource
    private MaintainManager maintainManager;
    @Resource
    private VehicleIllegalManager vehicleIllegalManager;

    @Override
    public boolean insert(VehicleDO vehicleDO) {
        return vehicleDao.insert(vehicleDO);
    }

    @Override
    public List<VehicleDO> query(VehicleQuery vehicleQuery) {
        return vehicleDao.query(vehicleQuery);
    }

    @Override
    public VehicleDO queryById(int id) {
        VehicleQuery vehicleQuery = new VehicleQuery();
        vehicleQuery.setId(id);
        List<VehicleDO> vehicleDOList = vehicleDao.query(vehicleQuery);
        return (vehicleDOList == null || vehicleDOList.size() == 0) ? null : vehicleDOList.get(0);
    }

    @Override
    public VehicleDO queryByVehicleNO(String vehicleNO) {
        VehicleQuery vehicleQuery = new VehicleQuery();
        vehicleQuery.setVehicleNO(vehicleNO);
        List<VehicleDO> vehicleDOList = vehicleDao.query(vehicleQuery);
        return (vehicleDOList == null || vehicleDOList.size() == 0) ? null : vehicleDOList.get(0);
    }

    @Override
    public int count(VehicleQuery vehicleQuery) {
        return vehicleDao.count(vehicleQuery);
    }

    @Override
    public boolean update(VehicleDO vehicleDO) {
        return vehicleDao.update(vehicleDO);
    }

    @Override
    public List<VehicleInfoDO> getVehicleInfo(List<VehicleDO> vehicleDOList, String vehicleNO, String team, Long startDate, Long endDate) {
        List<VehicleInfoDO> vehicleInfoDOList = new ArrayList<VehicleInfoDO>();
        if (vehicleDOList == null) {
            return vehicleInfoDOList;
        }
        Map<String, VehicleGasSumDO> vehicleGasSumMap = getVehicleGasSumMap(vehicleNO, team, startDate, endDate);
        Map<String, MaintainSumDO> vehicleMaintainSumMap = getVehicleMaintainSumMap(vehicleNO, team, startDate, endDate);
        Map<String, VehicleIllegalSumDO> vehicleIllegalSumMap = getVehicleIllegalSumMap(vehicleNO, team, startDate, endDate);
        for (VehicleDO vehicleDO : vehicleDOList) {
            VehicleInfoDO vehicleInfoDO = new VehicleInfoDO();
            vehicleInfoDO.setVehicleNO(vehicleDO.getVehicleNO());
            vehicleInfoDO.setTeam(vehicleDO.getTeam());
            VehicleGasSumDO vehicleGasSumDO = vehicleGasSumMap.get(vehicleDO.getVehicleNO());
            vehicleInfoDO.setGasMoney((vehicleGasSumDO == null || vehicleGasSumDO.getMoney() == null) ? 0: vehicleGasSumDO.getMoney());
            vehicleInfoDO.setGasAmount((vehicleGasSumDO == null || vehicleGasSumDO.getAmount() == null) ? 0: vehicleGasSumDO.getAmount());
            MaintainSumDO maintainSumDO = vehicleMaintainSumMap.get(vehicleDO.getVehicleNO());
            vehicleInfoDO.setWeiXiuMoney((maintainSumDO == null || maintainSumDO.getWeiXiuMoney() == null) ? 0 : maintainSumDO.getWeiXiuMoney());
            vehicleInfoDO.setWeiXiuNumber((maintainSumDO == null || maintainSumDO.getWeiXiuNumber() == null) ? 0 : maintainSumDO.getWeiXiuNumber());
            vehicleInfoDO.setBaoYangMoney((maintainSumDO == null || maintainSumDO.getBaoYangMoney() == null) ? 0 : maintainSumDO.getBaoYangMoney());
            vehicleInfoDO.setBaoYangNumber((maintainSumDO == null || maintainSumDO.getBaoYangNumber() == null) ? 0 : maintainSumDO.getBaoYangNumber());
            VehicleIllegalSumDO vehicleIllegalSumDO = vehicleIllegalSumMap.get(vehicleDO.getVehicleNO());
            vehicleInfoDO.setFineNumber((vehicleIllegalSumDO == null || vehicleIllegalSumDO.getNumber() == null) ? 0 : vehicleIllegalSumDO.getNumber());
            vehicleInfoDO.setFineMoney((vehicleIllegalSumDO == null || vehicleIllegalSumDO.getMoney() == null) ? 0 : vehicleIllegalSumDO.getMoney());
            vehicleInfoDO.setFinePoint((vehicleIllegalSumDO == null || vehicleIllegalSumDO.getPoint() == null) ? 0 : vehicleIllegalSumDO.getPoint());
            vehicleInfoDOList.add(vehicleInfoDO);
        }
        return vehicleInfoDOList;
    }

    private Map<String, VehicleGasSumDO> getVehicleGasSumMap(String vehicleNO, String team, Long startDate, Long endDate) {
        VehicleGasQuery vehicleGasQuery = new VehicleGasQuery();
        vehicleGasQuery.setStartDate(startDate == null ? null : CalendarUtil.formatDate(new Date(startDate), CalendarUtil.TIME_PATTERN));
        vehicleGasQuery.setEndDate(endDate == null ? null : CalendarUtil.formatDate(new Date(endDate), CalendarUtil.TIME_PATTERN));
        vehicleGasQuery.setVehicleNO(vehicleNO);
        vehicleGasQuery.setTeam(team);
        List<VehicleGasSumDO> list = vehicleGasManager.queryGroupByVehicle(vehicleGasQuery);
        Map<String, VehicleGasSumDO> vehicleGasSumMap = new HashMap<String, VehicleGasSumDO>();
        if (list == null) {
            return vehicleGasSumMap;
        }
        for (VehicleGasSumDO vehicleGasSumDO : list) {
            if (vehicleGasSumDO.getCost() == null) continue;
            BigDecimal bg = new BigDecimal(vehicleGasSumDO.getCost()).setScale(1, RoundingMode.HALF_UP);
            vehicleGasSumDO.setCost(bg.doubleValue());
        }

        for (VehicleGasSumDO vehicleGasSumDO : list) {
            vehicleGasSumMap.put(vehicleGasSumDO.getVehicleNO(), vehicleGasSumDO);
        }
        return vehicleGasSumMap;
    }

    private Map<String, MaintainSumDO> getVehicleMaintainSumMap(String vehicleNO, String team, Long startDate, Long endDate) {
        MaintainQuery maintainQuery = new MaintainQuery();
        maintainQuery.setStartDate(startDate == null ? null : new Date(startDate));
        maintainQuery.setEndDate(endDate == null ? null : new Date(endDate));
        maintainQuery.setVehicleNO(vehicleNO);
        maintainQuery.setTeam(team);
        List<String> statusList = new ArrayList<String>();
        statusList.add(SystemConstant.MAINTAIN_ALREADY_PICK_UP);
        maintainQuery.setStatusList(statusList);
        List<MaintainSumDO> list = maintainManager.queryGroupByVehicle(maintainQuery);
        Map<String, MaintainSumDO> vehicleMaintainSumMap = new HashMap<String, MaintainSumDO>();
        if (list == null) {
            return vehicleMaintainSumMap;
        }
        for (MaintainSumDO maintainSumDO : list) {
            vehicleMaintainSumMap.put(maintainSumDO.getVehicleNO(), maintainSumDO);
        }
        return vehicleMaintainSumMap;
    }

    private Map<String, VehicleIllegalSumDO> getVehicleIllegalSumMap(String vehicleNO, String team, Long startDate, Long endDate) {
        VehicleIllegalQuery vehicleIllegalQuery = new VehicleIllegalQuery();
        vehicleIllegalQuery.setTeam(team);
        vehicleIllegalQuery.setVehicleNO(vehicleNO);
        vehicleIllegalQuery.setStartDate(startDate == null ? null : CalendarUtil.formatDate(new Date(startDate), CalendarUtil.TIME_PATTERN));
        vehicleIllegalQuery.setEndDate(endDate == null ? null : CalendarUtil.formatDate(new Date(endDate), CalendarUtil.TIME_PATTERN));
        List<VehicleIllegalSumDO> list = vehicleIllegalManager.queryGroupByVehicle(vehicleIllegalQuery);
        Map<String, VehicleIllegalSumDO> vehicleIllegalSumMap = new HashMap<String, VehicleIllegalSumDO>();
        if (list == null) {
            return vehicleIllegalSumMap;
        }
        for (VehicleIllegalSumDO vehicleIllegalSumDO : list) {
            vehicleIllegalSumMap.put(vehicleIllegalSumDO.getVehicleNO(), vehicleIllegalSumDO);
        }
        return vehicleIllegalSumMap;
    }
}
