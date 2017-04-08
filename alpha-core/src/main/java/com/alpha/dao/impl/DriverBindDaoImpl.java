package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.DriverBindDao;
import com.alpha.domain.DriverBindDO;
import com.alpha.query.DriverBindQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("driverBindDao")
public class DriverBindDaoImpl implements DriverBindDao{

    private static final Logger logger = LoggerFactory.getLogger(DriverBindDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(DriverBindDO driverBindDO) {
        try {
            sqlSession.insert("driverBind.insert", driverBindDO);
            return true;
        } catch (Exception e) {
            logger.error("DriverBindDao insert catch exception, driverBindDO=" + JSON.toJSONString(driverBindDO), e);
            return false;
        }
    }

    @Override
    public List<DriverBindDO> query(DriverBindQuery driverBindQuery) {
        try {
            return sqlSession.selectList("driverBind.selectByPage", driverBindQuery);
        } catch (Exception e) {
            logger.error("DriverBindDao query catch exception, driverBindQuery=" + JSON.toJSONString(driverBindQuery), e);
            return null;
        }
    }

    @Override
    public boolean update(DriverBindDO driverBindDO) {
        try {
            sqlSession.update("driverBind.update", driverBindDO);
            return true;
        } catch (Exception e) {
            logger.error("DriverBindDao update catch exception, driverBindDO=" + JSON.toJSONString(driverBindDO), e);
            return false;
        }
    }
}
