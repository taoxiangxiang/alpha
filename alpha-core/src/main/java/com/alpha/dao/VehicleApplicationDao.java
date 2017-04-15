package com.alpha.dao;

import com.alpha.domain.VehicleApplicationDO;
import com.alpha.query.VehicleApplicationQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/6.
 */
public interface VehicleApplicationDao {

    boolean insert(VehicleApplicationDO vehicleApplicationDO);

    List<VehicleApplicationDO> query(VehicleApplicationQuery vehicleApplicationQuery);

    int count(VehicleApplicationQuery vehicleApplicationQuery);
}
