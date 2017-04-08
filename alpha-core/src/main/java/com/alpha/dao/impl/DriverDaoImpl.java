package com.alpha.dao.impl;

import com.alpha.dao.DriverDao;
import com.alpha.domain.DriverDO;
import com.alpha.query.DriverQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("driverDao")
public class DriverDaoImpl implements DriverDao {
    @Override
    public boolean insert(DriverDO driverDO) {
        return false;
    }

    @Override
    public List<DriverDO> query(DriverQuery driverQuery) {
        return null;
    }

    @Override
    public boolean update(DriverDO driverDO) {
        return false;
    }
}
