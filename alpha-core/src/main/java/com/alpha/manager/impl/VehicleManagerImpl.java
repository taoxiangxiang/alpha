package com.alpha.manager.impl;

import com.alpha.dao.VehicleDao;
import com.alpha.domain.VehicleDO;
import com.alpha.domain.VehicleUseSumDO;
import com.alpha.manager.VehicleManager;
import com.alpha.query.VehicleQuery;
import com.alpha.query.VehicleUseQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("vehicleManager")
public class VehicleManagerImpl implements VehicleManager {

    @Resource
    private VehicleDao vehicleDao;

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
}
