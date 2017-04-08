package com.alpha.manager;

import com.alpha.domain.DriverBindDO;
import com.alpha.query.DriverBindQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public interface DriverBindManager {

    boolean insert(DriverBindDO driverBindDO);

    List<DriverBindDO> query(DriverBindQuery driverBindQuery);

    boolean update(DriverBindDO driverBindDO);
}
