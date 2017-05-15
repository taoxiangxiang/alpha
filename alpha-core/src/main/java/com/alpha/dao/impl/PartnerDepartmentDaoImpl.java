package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.PartnerDepartmentDao;
import com.alpha.domain.DepartmentDO;
import com.alpha.domain.PartnerDepartmentDO;
import com.alpha.query.DepartmentQuery;
import com.alpha.query.PartnerDepartmentQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/5/9.
 */
@Component("partnerDepartmentDao")
public class PartnerDepartmentDaoImpl implements PartnerDepartmentDao {

    private static final Logger logger = LoggerFactory.getLogger(PartnerDepartmentDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(PartnerDepartmentDO partnerDepartmentDO) {
        try {
            sqlSession.insert("partnerDepartment.insert", partnerDepartmentDO);
            return true;
        } catch (Exception e) {
            logger.error("PartnerDepartmentDao insert catch exception, partnerDepartmentDO=" + JSON.toJSONString(partnerDepartmentDO), e);
            return false;
        }
    }

    @Override
    public List<PartnerDepartmentDO> query(PartnerDepartmentQuery partnerDepartmentQuery) {
        try {
            return sqlSession.selectList("partnerDepartment.selectByPage", partnerDepartmentQuery);
        } catch (Exception e) {
            logger.error("PartnerDepartmentDao query catch exception, partnerDepartmentQuery=" + JSON.toJSONString(partnerDepartmentQuery), e);
            return null;
        }
    }

    @Override
    public int count(PartnerDepartmentQuery partnerDepartmentQuery) {
        try {
            return (Integer)sqlSession.selectOne("partnerDepartment.count", partnerDepartmentQuery);
        } catch (Exception e) {
            logger.error("PartnerDepartmentDao count catch exception, partnerDepartmentQuery=" + JSON.toJSONString(partnerDepartmentQuery), e);
            return 0;
        }
    }

    @Override
    public boolean update(PartnerDepartmentDO partnerDepartmentDO) {
        try {
            sqlSession.update("partnerDepartment.update", partnerDepartmentDO);
            return true;
        } catch (Exception e) {
            logger.error("PartnerDepartmentDao update catch exception, partnerDepartmentDO=" + JSON.toJSONString(partnerDepartmentDO), e);
            return false;
        }
    }
}
