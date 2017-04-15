package com.alpha.manager.impl;

import com.alpha.dao.DriverDao;
import com.alpha.domain.DriverDO;
import com.alpha.manager.DriverManager;
import com.alpha.query.DriverQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("driverManager")
public class DriverManagerImpl implements DriverManager {

    @Resource
    private DriverDao driverDao;

    @Override
    public boolean insert(DriverDO driverDO) {
        return driverDao.insert(driverDO);
    }

    @Override
    public List<DriverDO> query(DriverQuery driverQuery) {
        return driverDao.query(driverQuery);
    }

    @Override
    public int count(DriverQuery driverQuery) {
        return driverDao.count(driverQuery);
    }

    @Override
    public boolean update(DriverDO driverDO) {
        return driverDao.update(driverDO);
    }
}
