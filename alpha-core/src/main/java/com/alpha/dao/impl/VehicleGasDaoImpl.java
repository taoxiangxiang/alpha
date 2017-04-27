package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.VehicleGasDao;
import com.alpha.domain.VehicleGasDO;
import com.alpha.query.VehicleGasQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
@Component("vehicleGasDao")
public class VehicleGasDaoImpl implements VehicleGasDao {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(VehicleGasDO vehicleGasDO) {
        try {
            sqlSession.insert("vehicleGas.insert", vehicleGasDO);
            return true;
        } catch (Exception e) {
            logger.error("VehicleGasDao insert catch exception, vehicleGasDO=" + JSON.toJSONString(vehicleGasDO), e);
            return false;
        }
    }

    @Override
    public List<VehicleGasDO> query(VehicleGasQuery vehicleGasQuery) {
        try {
            return sqlSession.selectList("vehicleGas.selectByPage", vehicleGasQuery);
        } catch (Exception e) {
            logger.error("VehicleGasDao query catch exception, vehicleGasQuery=" + JSON.toJSONString(vehicleGasQuery), e);
            return null;
        }
    }

    @Override
    public int count(VehicleGasQuery vehicleGasQuery) {
        try {
            return (Integer)sqlSession.selectOne("vehicleGas.count", vehicleGasQuery);
        } catch (Exception e) {
            logger.error("VehicleGasDao count catch exception, vehicleGasQuery=" + JSON.toJSONString(vehicleGasQuery), e);
            return 0;
        }
    }

    @Override
    public boolean update(VehicleGasDO vehicleGasDO) {
        try {
            sqlSession.update("vehicleGas.update", vehicleGasDO);
            return true;
        } catch (Exception e) {
            logger.error("VehicleGasDao update catch exception, vehicleGasDO=" + JSON.toJSONString(vehicleGasDO), e);
            return false;
        }
    }
}
