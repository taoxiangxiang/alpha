package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.VehicleIllegalDao;
import com.alpha.domain.VehicleIllegalDO;
import com.alpha.domain.VehicleIllegalSumDO;
import com.alpha.query.VehicleIllegalQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
@Component("vehicleIllegalDao")
public class VehicleIllegalDaoImpl implements VehicleIllegalDao {

    private static final Logger logger = LoggerFactory.getLogger(VehicleIllegalDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(VehicleIllegalDO vehicleIllegalDO) {
        try {
            sqlSession.insert("vehicleIllegal.insert", vehicleIllegalDO);
            return true;
        } catch (Exception e) {
            logger.error("VehicleIllegalDao insert catch exception, vehicleIllegalDO=" + JSON.toJSONString(vehicleIllegalDO), e);
            return false;
        }
    }

    @Override
    public List<VehicleIllegalDO> query(VehicleIllegalQuery vehicleIllegalQuery) {
        try {
            return sqlSession.selectList("vehicleIllegal.selectByPage", vehicleIllegalQuery);
        } catch (Exception e) {
            logger.error("VehicleIllegalDao query catch exception, vehicleIllegalQuery=" + JSON.toJSONString(vehicleIllegalQuery), e);
            return null;
        }
    }

    @Override
    public int count(VehicleIllegalQuery vehicleIllegalQuery) {
        try {
            return (Integer)sqlSession.selectOne("vehicleIllegal.count", vehicleIllegalQuery);
        } catch (Exception e) {
            logger.error("VehicleIllegalDao count catch exception, vehicleIllegalQuery=" + JSON.toJSONString(vehicleIllegalQuery), e);
            return 0;
        }
    }

    @Override
    public boolean update(VehicleIllegalDO vehicleIllegalDO) {
        try {
            sqlSession.update("vehicleIllegal.update", vehicleIllegalDO);
            return true;
        } catch (Exception e) {
            logger.error("VehicleIllegalDao update catch exception, vehicleIllegalDO=" + JSON.toJSONString(vehicleIllegalDO), e);
            return false;
        }
    }

    @Override
    public List<VehicleIllegalSumDO> queryGroupByVehicle(VehicleIllegalQuery vehicleIllegalQuery) {
        try {
            return sqlSession.selectList("vehicleIllegal.selectSumByPage", vehicleIllegalQuery);
        } catch (Exception e) {
            logger.error("VehicleIllegalDao query catch exception, vehicleIllegalQuery=" + JSON.toJSONString(vehicleIllegalQuery), e);
            return null;
        }
    }

    @Override
    public int countGroupByVehicle(VehicleIllegalQuery vehicleIllegalQuery) {
        try {
            return (Integer)sqlSession.selectOne("vehicleIllegal.countSum", vehicleIllegalQuery);
        } catch (Exception e) {
            logger.error("VehicleIllegalDao count catch exception, vehicleIllegalQuery=" + JSON.toJSONString(vehicleIllegalQuery), e);
            return 0;
        }
    }
}
