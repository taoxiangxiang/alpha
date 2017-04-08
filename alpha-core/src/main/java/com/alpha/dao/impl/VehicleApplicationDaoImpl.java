package com.alpha.dao.impl;

import com.alpha.dao.VehicleApplicationDao;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.query.VehicleApplicationQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/6.
 */
@Component("vehicleApplicationDao")
public class VehicleApplicationDaoImpl implements VehicleApplicationDao {

    @Override
    public boolean insert(VehicleApplicationDO vehicleApplicationDO) {
        return false;
    }

    @Override
    public List<VehicleApplicationDO> query(VehicleApplicationQuery vehicleApplicationQuery) {
        return null;
    }
}
