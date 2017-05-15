package com.alpha.manager;

import com.alpha.domain.VehicleUseDO;
import com.alpha.domain.VehicleUseSumDO;
import com.alpha.query.VehicleUseQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/16.
 */
public interface VehicleUseManager {

    String batchInsert(List<VehicleUseDO> vehicleUseDOList) throws Exception;

    boolean insert(VehicleUseDO vehicleUseDO);

    List<VehicleUseDO> query(VehicleUseQuery vehicleUseQuery);

    int count(VehicleUseQuery vehicleUseQuery);

    boolean update(VehicleUseDO vehicleUseDO);

    List<VehicleUseSumDO> queryGroupByDriver(VehicleUseQuery vehicleUseQuery);

    int countGroupByDriver(VehicleUseQuery vehicleUseQuery);
}
