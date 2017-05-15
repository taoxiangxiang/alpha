package com.alpha.dao;

import com.alpha.domain.VehicleGasDO;
import com.alpha.domain.VehicleGasSumDO;
import com.alpha.query.VehicleGasQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
public interface VehicleGasDao {

    boolean insert(VehicleGasDO vehicleGasDO);

    List<VehicleGasDO> query(VehicleGasQuery vehicleGasQuery);

    int count(VehicleGasQuery vehicleGasQuery);

    boolean update(VehicleGasDO vehicleGasDO);

    List<VehicleGasSumDO> queryGroupByVehicle(VehicleGasQuery vehicleGasQuery);

    int countGroupByVehicle(VehicleGasQuery vehicleGasQuery);
}
