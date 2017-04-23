package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.VehicleApplicationDao;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.query.VehicleApplicationQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/6.
 */
@Component("vehicleApplicationDao")
public class VehicleApplicationDaoImpl implements VehicleApplicationDao {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(VehicleApplicationDO vehicleApplicationDO) {
        try {
            sqlSession.insert("vehicleApplication.insert", vehicleApplicationDO);
            return true;
        } catch (Exception e) {
            logger.error("VehicleApplicationDao insert catch exception, vehicleApplicationDO=" + JSON.toJSONString(vehicleApplicationDO), e);
            return false;
        }
    }

    @Override
    public List<VehicleApplicationDO> query(VehicleApplicationQuery vehicleApplicationQuery) {
        try {
            return sqlSession.selectList("vehicleApplication.selectByPage", vehicleApplicationQuery);
        } catch (Exception e) {
            logger.error("VehicleApplicationDao query catch exception, vehicleApplicationQuery=" + JSON.toJSONString(vehicleApplicationQuery), e);
            return null;
        }
    }

    @Override
    public int count(VehicleApplicationQuery vehicleApplicationQuery) {
        try {
            return (Integer)sqlSession.selectOne("vehicleApplication.count", vehicleApplicationQuery);
        } catch (Exception e) {
            logger.error("VehicleApplicationDao count catch exception, vehicleApplicationQuery=" + JSON.toJSONString(vehicleApplicationQuery), e);
            return 0;
        }
    }

    @Override
    public boolean update(VehicleApplicationDO vehicleApplicationDO) {
        try {
            sqlSession.update("vehicleApplication.update", vehicleApplicationDO);
            return true;
        } catch (Exception e) {
            logger.error("VehicleApplicationDao update catch exception, vehicleApplicationDO=" + JSON.toJSONString(vehicleApplicationDO), e);
            return false;
        }
    }
}
