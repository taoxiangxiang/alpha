package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.VehicleCheckDao;
import com.alpha.domain.VehicleCheckDO;
import com.alpha.query.VehicleCheckQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Component("vehicleCheckDao")
public class VehicleCheckDaoImpl implements VehicleCheckDao {

    private static final Logger logger = LoggerFactory.getLogger(VehicleCheckDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(VehicleCheckDO vehicleCheckDO) {
        try {
            sqlSession.insert("vehicleCheck.insert", vehicleCheckDO);
            return true;
        } catch (Exception e) {
            logger.error("VehicleCheckDao insert catch exception, vehicleCheckDO=" + JSON.toJSONString(vehicleCheckDO), e);
            return false;
        }
    }

    @Override
    public List<VehicleCheckDO> query(VehicleCheckQuery vehicleCheckQuery) {
        try {
            return sqlSession.selectList("vehicleCheck.selectByPage", vehicleCheckQuery);
        } catch (Exception e) {
            logger.error("VehicleCheckDao query catch exception, vehicleCheckQuery=" + JSON.toJSONString(vehicleCheckQuery), e);
            return null;
        }
    }

    @Override
    public int count(VehicleCheckQuery vehicleCheckQuery) {
        try {
            return (Integer)sqlSession.selectOne("vehicleCheck.count", vehicleCheckQuery);
        } catch (Exception e) {
            logger.error("VehicleCheckDao count catch exception, vehicleCheckQuery=" + JSON.toJSONString(vehicleCheckQuery), e);
            return 0;
        }
    }

    @Override
    public boolean update(VehicleCheckDO vehicleCheckDO) {
        try {
            sqlSession.update("vehicleCheck.update", vehicleCheckDO);
            return true;
        } catch (Exception e) {
            logger.error("VehicleCheckDao update catch exception, vehicleCheckDO=" + JSON.toJSONString(vehicleCheckDO), e);
            return false;
        }
    }
}
