package com.alpha.manager;

import com.alpha.domain.VehicleIllegalDO;
import com.alpha.domain.VehicleIllegalSumDO;
import com.alpha.query.VehicleIllegalQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
public interface VehicleIllegalManager {

    boolean insert(VehicleIllegalDO vehicleIllegalDO);

    List<VehicleIllegalDO> query(VehicleIllegalQuery vehicleIllegalQuery);

    int count(VehicleIllegalQuery vehicleIllegalQuery);

    boolean update(VehicleIllegalDO vehicleIllegalDO);

    List<VehicleIllegalSumDO> queryGroupByVehicle(VehicleIllegalQuery vehicleIllegalQuery);

    int countGroupByVehicle(VehicleIllegalQuery vehicleIllegalQuery);
}
