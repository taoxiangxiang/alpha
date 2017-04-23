package com.alpha.manager.impl;

import com.alpha.dao.VehicleApplicationDao;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.query.VehicleApplicationQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/6.
 */
@Component("vehicleApplicationManager")
public class VehicleApplicationManagerImpl implements VehicleApplicationManager {

    @Resource
    private VehicleApplicationDao vehicleApplicationDao;

    @Override
    public boolean insert(VehicleApplicationDO vehicleApplicationDO) {
        return vehicleApplicationDao.insert(vehicleApplicationDO);
    }

    @Override
    public List<VehicleApplicationDO> query(VehicleApplicationQuery vehicleApplicationQuery) {
        return vehicleApplicationDao.query(vehicleApplicationQuery);
    }

    @Override
    public int count(VehicleApplicationQuery vehicleApplicationQuery) {
        return vehicleApplicationDao.count(vehicleApplicationQuery);
    }

    @Override
    public boolean update(VehicleApplicationDO vehicleApplicationDO) {
        return vehicleApplicationDao.update(vehicleApplicationDO);
    }
}
