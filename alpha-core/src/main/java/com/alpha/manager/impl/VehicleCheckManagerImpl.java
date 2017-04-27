package com.alpha.manager.impl;

import com.alpha.dao.VehicleCheckDao;
import com.alpha.domain.VehicleCheckDO;
import com.alpha.manager.VehicleCheckManager;
import com.alpha.query.VehicleCheckQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Component("vehicleCheckManager")
public class VehicleCheckManagerImpl implements VehicleCheckManager {

    @Resource
    private VehicleCheckDao vehicleCheckDao;

    @Override
    public boolean insert(VehicleCheckDO vehicleCheckDO) {
        return vehicleCheckDao.insert(vehicleCheckDO);
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
    public boolean update(VehicleCheckDO vehicleCheckDO) {
        return vehicleCheckDao.update(vehicleCheckDO);
    }
}
