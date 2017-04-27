package com.alpha.dao;

import com.alpha.domain.VehicleCheckDO;
import com.alpha.query.VehicleCheckQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
public interface VehicleCheckDao {

    boolean insert(VehicleCheckDO vehicleCheckDO);

    List<VehicleCheckDO> query(VehicleCheckQuery vehicleCheckQuery);

    int count(VehicleCheckQuery vehicleCheckQuery);

    boolean update(VehicleCheckDO vehicleCheckDO);
}
