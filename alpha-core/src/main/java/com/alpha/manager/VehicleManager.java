package com.alpha.manager;

import com.alpha.domain.VehicleDO;
import com.alpha.domain.VehicleInfoDO;
import com.alpha.domain.VehicleUseSumDO;
import com.alpha.query.VehicleQuery;
import com.alpha.query.VehicleUseQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public interface VehicleManager {

    boolean insert(VehicleDO vehicleDO);

    List<VehicleDO> query(VehicleQuery vehicleQuery);

    VehicleDO queryById(int id);

    VehicleDO queryByVehicleNO(String vehicleNO);

    int count(VehicleQuery vehicleQuery);

    boolean update(VehicleDO vehicleDO);

    List<VehicleInfoDO> getVehicleInfo(List<VehicleDO> vehicleDOList, String vehicleNO, String team, Long startDate, Long endDate);
}
