package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.VehicleAccidentDao;
import com.alpha.domain.VehicleAccidentDO;
import com.alpha.query.VehicleAccidentQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
@Component("vehicleAccidentDao")
public class VehicleAccidentDaoImpl implements VehicleAccidentDao {

    private static final Logger logger = LoggerFactory.getLogger(VehicleAccidentDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(VehicleAccidentDO vehicleAccidentDO) {
        try {
            sqlSession.insert("vehicleAccident.insert", vehicleAccidentDO);
            return true;
        } catch (Exception e) {
            logger.error("VehicleAccidentDao insert catch exception, vehicleAccidentDO=" + JSON.toJSONString(vehicleAccidentDO), e);
            return false;
        }
    }

    @Override
    public List<VehicleAccidentDO> query(VehicleAccidentQuery vehicleAccidentQuery) {
        try {
            return sqlSession.selectList("vehicleAccident.selectByPage", vehicleAccidentQuery);
        } catch (Exception e) {
            logger.error("VehicleAccidentDao query catch exception, vehicleAccidentQuery=" + JSON.toJSONString(vehicleAccidentQuery), e);
            return null;
        }
    }

    @Override
    public int count(VehicleAccidentQuery vehicleAccidentQuery) {
        try {
            return (Integer)sqlSession.selectOne("vehicleAccident.count", vehicleAccidentQuery);
        } catch (Exception e) {
            logger.error("VehicleAccidentDao count catch exception, vehicleAccidentQuery=" + JSON.toJSONString(vehicleAccidentQuery), e);
            return 0;
        }
    }

    @Override
    public boolean update(VehicleAccidentDO vehicleAccidentDO) {
        try {
            sqlSession.update("vehicleAccident.update", vehicleAccidentDO);
            return true;
        } catch (Exception e) {
            logger.error("VehicleAccidentDao update catch exception, vehicleAccidentDO=" + JSON.toJSONString(vehicleAccidentDO), e);
            return false;
        }
    }
}
