package com.alpha.dao;

import com.alpha.domain.DriverDO;
import com.alpha.query.DriverQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public interface DriverDao {

    boolean insert(DriverDO driverDO);

    List<DriverDO> query(DriverQuery driverQuery);

    int count(DriverQuery driverQuery);

    boolean update(DriverDO driverDO);
}
