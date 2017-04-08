package com.alpha.dao.impl;

import com.alpha.dao.DriverBindDao;
import com.alpha.domain.DriverBindDO;
import com.alpha.query.DriverBindQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("driverBindDao")
public class DriverBindDaoImpl implements DriverBindDao{
    @Override
    public boolean insert(DriverBindDO driverBindDO) {
        return false;
    }

    @Override
    public List<DriverBindDO> query(DriverBindQuery driverBindQuery) {
        return null;
    }

    @Override
    public boolean update(DriverBindDO driverBindDO) {
        return false;
    }
}
