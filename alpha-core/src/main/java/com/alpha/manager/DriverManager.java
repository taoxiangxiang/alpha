package com.alpha.manager;

import com.alpha.domain.DriverDO;
import com.alpha.query.DriverQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public interface DriverManager {
    boolean insert(DriverDO driverDO) throws Exception;

    List<DriverDO> query(DriverQuery driverQuery);

    DriverDO queryById(int id);

    DriverDO queryByCitizenId(String citizenId);

    int count(DriverQuery driverQuery);

    boolean update(DriverDO driverDO);
}
