package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.VehicleUseDao;
import com.alpha.domain.VehicleUseDO;
import com.alpha.domain.VehicleUseSumDO;
import com.alpha.query.VehicleUseQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/16.
 */
@Component("vehicleUseDao")
public class VehicleUseDaoImpl implements VehicleUseDao {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(VehicleUseDO vehicleUseDO) {
        try {
            sqlSession.insert("vehicleUse.insert", vehicleUseDO);
            return true;
        } catch (Exception e) {
            logger.error("VehicleUseDao insert catch exception, vehicleUseDO=" + JSON.toJSONString(vehicleUseDO), e);
            return false;
        }
    }

    @Override
    public List<VehicleUseDO> query(VehicleUseQuery vehicleUseQuery) {
        try {
            return sqlSession.selectList("vehicleUse.selectByPage", vehicleUseQuery);
        } catch (Exception e) {
            logger.error("VehicleUseDao query catch exception, vehicleUseQuery=" + JSON.toJSONString(vehicleUseQuery), e);
            return null;
        }
    }

    @Override
    public int count(VehicleUseQuery vehicleUseQuery) {
        try {
            return (Integer)sqlSession.selectOne("vehicleUse.count", vehicleUseQuery);
        } catch (Exception e) {
            logger.error("VehicleUseDao count catch exception, vehicleUseQuery=" + JSON.toJSONString(vehicleUseQuery), e);
            return 0;
        }
    }

    @Override
    public boolean update(VehicleUseDO vehicleUseDO) {
        try {
            sqlSession.update("vehicleUse.update", vehicleUseDO);
            return true;
        } catch (Exception e) {
            logger.error("VehicleUseDao update catch exception, vehicleUseDO=" + JSON.toJSONString(vehicleUseDO), e);
            return false;
        }
    }

    @Override
    public List<VehicleUseSumDO> queryGroupByDriver(VehicleUseQuery vehicleUseQuery) {
        try {
            return sqlSession.selectList("vehicleUse.selectSumByPage", vehicleUseQuery);
        } catch (Exception e) {
            logger.error("VehicleUseDao query catch exception, vehicleUseQuery=" + JSON.toJSONString(vehicleUseQuery), e);
            return null;
        }
    }

    @Override
    public int countGroupByDriver(VehicleUseQuery vehicleUseQuery) {
        try {
            return (Integer)sqlSession.selectOne("vehicleUse.countSum", vehicleUseQuery);
        } catch (Exception e) {
            logger.error("VehicleUseDao count catch exception, vehicleUseQuery=" + JSON.toJSONString(vehicleUseQuery), e);
            return 0;
        }
    }
}
