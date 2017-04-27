package com.alpha.manager.impl;

import com.alpha.dao.VehicleAccidentDao;
import com.alpha.domain.VehicleAccidentDO;
import com.alpha.manager.VehicleAccidentManager;
import com.alpha.query.VehicleAccidentQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
@Component("vehicleAccidentManager")
public class VehicleAccidentManagerImpl implements VehicleAccidentManager {

    @Resource
    private VehicleAccidentDao vehicleAccidentDao;

    @Override
    public boolean insert(VehicleAccidentDO vehicleAccidentDO) {
        return vehicleAccidentDao.insert(vehicleAccidentDO);
    }

    @Override
    public List<VehicleAccidentDO> query(VehicleAccidentQuery vehicleAccidentQuery) {
        return vehicleAccidentDao.query(vehicleAccidentQuery);
    }

    @Override
    public int count(VehicleAccidentQuery vehicleAccidentQuery) {
        return vehicleAccidentDao.count(vehicleAccidentQuery);
    }

    @Override
    public boolean update(VehicleAccidentDO vehicleAccidentDO) {
        return vehicleAccidentDao.update(vehicleAccidentDO);
    }
}
