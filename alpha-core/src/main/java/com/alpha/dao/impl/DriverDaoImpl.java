package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.DriverDao;
import com.alpha.domain.DriverDO;
import com.alpha.query.DriverQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("driverDao")
public class DriverDaoImpl implements DriverDao {

    private static final Logger logger = LoggerFactory.getLogger(DriverDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(DriverDO driverDO) {
        try {
            sqlSession.insert("driver.insert", driverDO);
            return true;
        } catch (Exception e) {
            logger.error("DriverDao insert catch exception, driverDO=" + JSON.toJSONString(driverDO), e);
            return false;
        }
    }

    @Override
    public List<DriverDO> query(DriverQuery driverQuery) {
        try {
            return sqlSession.selectList("driver.selectByPage", driverQuery);
        } catch (Exception e) {
            logger.error("DriverDao query catch exception, driverQuery=" + JSON.toJSONString(driverQuery), e);
            return null;
        }
    }

    @Override
    public boolean update(DriverDO driverDO) {
        try {
            sqlSession.update("driver.update", driverDO);
            return true;
        } catch (Exception e) {
            logger.error("DriverDao update catch exception, driverDO=" + JSON.toJSONString(driverDO), e);
            return false;
        }
    }
}
