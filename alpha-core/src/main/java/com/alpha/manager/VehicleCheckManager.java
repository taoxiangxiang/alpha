package com.alpha.manager;

import com.alpha.domain.VehicleCheckDO;
import com.alpha.query.VehicleCheckQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
public interface VehicleCheckManager {

    boolean insert(VehicleCheckDO vehicleCheckDO) throws Exception ;

    List<VehicleCheckDO> query(VehicleCheckQuery vehicleCheckQuery);

    int count(VehicleCheckQuery vehicleCheckQuery);

    boolean update(VehicleCheckDO vehicleCheckDO) throws Exception ;
}
