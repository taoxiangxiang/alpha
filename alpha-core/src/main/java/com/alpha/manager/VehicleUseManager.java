package com.alpha.manager;

import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleUseDO;
import com.alpha.domain.VehicleUseSumDO;
import com.alpha.query.VehicleUseQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/16.
 */
public interface VehicleUseManager {

    String batchInsert(VehicleApplicationDO vehicleApplicationDO, List<VehicleUseDO> vehicleUseDOList) throws Exception;

    boolean insert(VehicleUseDO vehicleUseDO);

    List<VehicleUseDO> query(VehicleUseQuery vehicleUseQuery);

    VehicleUseDO queryById(int id);

    int count(VehicleUseQuery vehicleUseQuery);

    boolean update(VehicleUseDO vehicleUseDO) throws Exception;

    List<VehicleUseSumDO> queryGroupByDriver(VehicleUseQuery vehicleUseQuery);

    int countGroupByDriver(VehicleUseQuery vehicleUseQuery);
}
