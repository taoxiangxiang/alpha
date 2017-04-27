package com.alpha.manager;

import com.alpha.domain.VehicleGasDO;
import com.alpha.query.VehicleGasQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
public interface VehicleGasManager {

    boolean insert(VehicleGasDO vehicleGasDO);

    List<VehicleGasDO> query(VehicleGasQuery vehicleGasQuery);

    int count(VehicleGasQuery vehicleGasQuery);

    boolean update(VehicleGasDO vehicleGasDO);
}
