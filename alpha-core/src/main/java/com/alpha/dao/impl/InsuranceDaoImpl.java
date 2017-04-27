package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.InsuranceDao;
import com.alpha.domain.InsuranceDO;
import com.alpha.query.InsuranceQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Component("insuranceDao")
public class InsuranceDaoImpl implements InsuranceDao {

    private static final Logger logger = LoggerFactory.getLogger(InsuranceDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(InsuranceDO insuranceDO) {
        try {
            sqlSession.insert("insurance.insert", insuranceDO);
            return true;
        } catch (Exception e) {
            logger.error("InsuranceDao insert catch exception, insuranceDO=" + JSON.toJSONString(insuranceDO), e);
            return false;
        }
    }

    @Override
    public List<InsuranceDO> query(InsuranceQuery insuranceQuery) {
        try {
            return sqlSession.selectList("insurance.selectByPage", insuranceQuery);
        } catch (Exception e) {
            logger.error("InsuranceDao query catch exception, insuranceQuery=" + JSON.toJSONString(insuranceQuery), e);
            return null;
        }
    }

    @Override
    public int count(InsuranceQuery insuranceQuery) {
        try {
            return (Integer)sqlSession.selectOne("insurance.count", insuranceQuery);
        } catch (Exception e) {
            logger.error("InsuranceDao count catch exception, insuranceQuery=" + JSON.toJSONString(insuranceQuery), e);
            return 0;
        }
    }

    @Override
    public boolean update(InsuranceDO insuranceDO) {
        try {
            sqlSession.update("insurance.update", insuranceDO);
            return true;
        } catch (Exception e) {
            logger.error("InsuranceDao update catch exception, insuranceDO=" + JSON.toJSONString(insuranceDO), e);
            return false;
        }
    }
}
