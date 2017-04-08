package com.alpha.manager.impl;

import com.alpha.dao.DriverBindDao;
import com.alpha.domain.DriverBindDO;
import com.alpha.manager.DriverBindManager;
import com.alpha.query.DriverBindQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class DriverBindManagerImpl implements DriverBindManager {

    @Resource
    private DriverBindDao driverBindDao;

    @Override
    public boolean insert(DriverBindDO driverBindDO) {
        return driverBindDao.insert(driverBindDO);
    }

    @Override
    public List<DriverBindDO> query(DriverBindQuery driverBindQuery) {
        return driverBindDao.query(driverBindQuery);
    }

    @Override
    public boolean update(DriverBindDO driverBindDO) {
        return driverBindDao.update(driverBindDO);
    }
}
