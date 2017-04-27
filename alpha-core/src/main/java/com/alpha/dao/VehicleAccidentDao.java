package com.alpha.dao;

import com.alpha.domain.VehicleAccidentDO;
import com.alpha.query.VehicleAccidentQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
public interface VehicleAccidentDao {

    boolean insert(VehicleAccidentDO vehicleAccidentDO);

    List<VehicleAccidentDO> query(VehicleAccidentQuery vehicleAccidentQuery);

    int count(VehicleAccidentQuery vehicleAccidentQuery);

    boolean update(VehicleAccidentDO vehicleAccidentDO);
}
