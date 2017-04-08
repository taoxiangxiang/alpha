package com.alpha.dao;

import com.alpha.domain.DriverBindDO;
import com.alpha.domain.DriverDO;
import com.alpha.query.DriverBindQuery;
import com.alpha.query.DriverQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public interface DriverBindDao {

    boolean insert(DriverBindDO driverBindDO);

    List<DriverBindDO> query(DriverBindQuery driverBindQuery);

    boolean update(DriverBindDO driverBindDO);
}
