package com.alpha.manager.impl;

import com.alpha.dao.VehicleIllegalDao;
import com.alpha.domain.VehicleIllegalDO;
import com.alpha.domain.VehicleIllegalSumDO;
import com.alpha.manager.VehicleIllegalManager;
import com.alpha.query.VehicleIllegalQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
@Component("vehicleIllegalManager")
public class VehicleIllegalManagerImpl implements VehicleIllegalManager {

    @Resource
    private VehicleIllegalDao vehicleIllegalDao;

    @Override
    public boolean insert(VehicleIllegalDO vehicleIllegalDO) {
        return vehicleIllegalDao.insert(vehicleIllegalDO);
    }

    @Override
    public List<VehicleIllegalDO> query(VehicleIllegalQuery vehicleIllegalQuery) {
        return vehicleIllegalDao.query(vehicleIllegalQuery);
    }

    @Override
    public int count(VehicleIllegalQuery vehicleIllegalQuery) {
        return vehicleIllegalDao.count(vehicleIllegalQuery);
    }

    @Override
    public boolean update(VehicleIllegalDO vehicleIllegalDO) {
        return vehicleIllegalDao.update(vehicleIllegalDO);
    }

    @Override
    public List<VehicleIllegalSumDO> queryGroupByVehicle(VehicleIllegalQuery vehicleIllegalQuery) {
        return vehicleIllegalDao.queryGroupByVehicle(vehicleIllegalQuery);
    }

    @Override
    public int countGroupByVehicle(VehicleIllegalQuery vehicleIllegalQuery) {
        return vehicleIllegalDao.countGroupByVehicle(vehicleIllegalQuery);
    }
}
