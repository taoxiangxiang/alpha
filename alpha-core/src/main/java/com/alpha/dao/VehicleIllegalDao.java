package com.alpha.dao;

import com.alpha.domain.VehicleIllegalDO;
import com.alpha.query.VehicleIllegalQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
public interface VehicleIllegalDao {

    boolean insert(VehicleIllegalDO vehicleIllegalDO);

    List<VehicleIllegalDO> query(VehicleIllegalQuery vehicleIllegalQuery);

    int count(VehicleIllegalQuery vehicleIllegalQuery);

    boolean update(VehicleIllegalDO vehicleIllegalDO);
}
