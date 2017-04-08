package com.alpha.dao.impl;

import com.alpha.dao.VehicleDao;
import com.alpha.domain.VehicleDO;
import com.alpha.query.VehicleQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("vehicleDao")
public class VehicleDaoImpl implements VehicleDao{

    @Override
    public boolean insert(VehicleDO vehicleDO) {
        return false;
    }

    @Override
    public List<VehicleDO> query(VehicleQuery vehicleQuery) {
        return null;
    }

    @Override
    public boolean update(VehicleDO vehicleDO) {
        return false;
    }
}
