package com.alpha.manager.impl;

import com.alpha.dao.VehicleCheckDao;
import com.alpha.domain.VehicleCheckDO;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.VehicleCheckManager;
import com.alpha.manager.VehicleManager;
import com.alpha.query.VehicleCheckQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Component("vehicleCheckManager")
public class VehicleCheckManagerImpl implements VehicleCheckManager {

    @Resource
    private VehicleCheckDao vehicleCheckDao;
    @Resource
    private VehicleManager vehicleManager;

    @Override
    @Transactional(rollbackForClassName="Exception")
    public boolean insert(VehicleCheckDO vehicleCheckDO) throws Exception {
        VehicleDO vehicleDO = vehicleManager.queryByVehicleNO(vehicleCheckDO.getVehicleNO());
        if (vehicleDO == null) {
            return false;
        }
        if (!vehicleCheckDao.insert(vehicleCheckDO)) {
            return false;
        }
        if (vehicleCheckDO.getEndDate().after(vehicleDO.getCheckDate())) {
            vehicleDO.setCheckDate(vehicleCheckDO.getEndDate());
            if (!vehicleManager.update(vehicleDO)) {
                throw new Exception("数据库写入失败");
            }
        }
        return true;
    }

    @Override
    public List<VehicleCheckDO> query(VehicleCheckQuery vehicleCheckQuery) {
        return vehicleCheckDao.query(vehicleCheckQuery);
    }

    @Override
    public int count(VehicleCheckQuery vehicleCheckQuery) {
        return vehicleCheckDao.count(vehicleCheckQuery);
    }

    @Override
    @Transactional(rollbackForClassName="Exception")
    public boolean update(VehicleCheckDO vehicleCheckDO) throws Exception {
        VehicleDO vehicleDO = vehicleManager.queryByVehicleNO(vehicleCheckDO.getVehicleNO());
        if (vehicleDO == null) {
            return false;
        }
        if (!vehicleCheckDao.update(vehicleCheckDO)) {
            return false;
        }
        if (vehicleCheckDO.getEndDate().after(vehicleDO.getCheckDate())) {
            vehicleDO.setCheckDate(vehicleCheckDO.getEndDate());
            if (!vehicleManager.update(vehicleDO)) {
                throw new Exception("数据库写入失败");
            }
        }
        return true;
    }
}
