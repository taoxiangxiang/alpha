package com.alpha.dao;

import com.alpha.domain.VehicleDO;
import com.alpha.query.VehicleQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public interface VehicleDao {

    boolean insert(VehicleDO vehicleDO);

    List<VehicleDO> query(VehicleQuery vehicleQuery);

    int count(VehicleQuery vehicleQuery);

    boolean update(VehicleDO vehicleDO);
}
