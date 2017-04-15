package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.VehicleDao;
import com.alpha.domain.VehicleDO;
import com.alpha.query.VehicleQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("vehicleDao")
public class VehicleDaoImpl implements VehicleDao{

    private static final Logger logger = LoggerFactory.getLogger(VehicleDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(VehicleDO vehicleDO) {
        try {
            sqlSession.insert("vehicle.insert", vehicleDO);
            return true;
        } catch (Exception e) {
            logger.error("VehicleDao insert catch exception, vehicleDO=" + JSON.toJSONString(vehicleDO), e);
            return false;
        }
    }

    @Override
    public List<VehicleDO> query(VehicleQuery vehicleQuery) {
        try {
            return sqlSession.selectList("vehicle.selectByPage", vehicleQuery);
        } catch (Exception e) {
            logger.error("VehicleDao query catch exception, vehicleQuery=" + JSON.toJSONString(vehicleQuery), e);
            return null;
        }
    }

    @Override
    public int count(VehicleQuery vehicleQuery) {
        try {
            return (Integer) sqlSession.selectOne("vehicle.count", vehicleQuery);
        } catch (Exception e) {
            logger.error("VehicleDao count catch exception, vehicleQuery=" + JSON.toJSONString(vehicleQuery), e);
            return 0;
        }
    }

    @Override
    public boolean update(VehicleDO vehicleDO) {
        try {
            sqlSession.update("vehicle.update", vehicleDO);
            return true;
        } catch (Exception e) {
            logger.error("VehicleDao update catch exception, vehicleDO=" + JSON.toJSONString(vehicleDO), e);
            return false;
        }
    }
}
