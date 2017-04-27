package com.alpha.manager.impl;

import com.alpha.dao.VehicleGasDao;
import com.alpha.domain.VehicleGasDO;
import com.alpha.manager.VehicleGasManager;
import com.alpha.query.VehicleGasQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
@Component("vehicleGasManager")
public class VehicleGasManagerImpl implements VehicleGasManager {

    @Resource
    private VehicleGasDao vehicleGasDao;

    @Override
    public boolean insert(VehicleGasDO vehicleGasDO) {
        return vehicleGasDao.insert(vehicleGasDO);
    }

    @Override
    public List<VehicleGasDO> query(VehicleGasQuery vehicleGasQuery) {
        return vehicleGasDao.query(vehicleGasQuery);
    }

    @Override
    public int count(VehicleGasQuery vehicleGasQuery) {
        return vehicleGasDao.count(vehicleGasQuery);
    }

    @Override
    public boolean update(VehicleGasDO vehicleGasDO) {
        return vehicleGasDao.update(vehicleGasDO);
    }
}
