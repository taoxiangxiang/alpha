package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.DepartmentDao;
import com.alpha.domain.DepartmentDO;
import com.alpha.query.DepartmentQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("departmentDao")
public class DepartmentDaoImpl implements DepartmentDao {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(DepartmentDO departmentDO) {
        try {
            sqlSession.insert("department.insert", departmentDO);
            return true;
        } catch (Exception e) {
            logger.error("DepartmentDao insert catch exception, departmentDO=" + JSON.toJSONString(departmentDO), e);
            return false;
        }
    }

    @Override
    public List<DepartmentDO> query(DepartmentQuery departmentQuery) {
        try {
            return sqlSession.selectList("department.selectByPage", departmentQuery);
        } catch (Exception e) {
            logger.error("DepartmentDao query catch exception, departmentQuery=" + JSON.toJSONString(departmentQuery), e);
            return null;
        }
    }

    @Override
    public boolean update(DepartmentDO departmentDO) {
        try {
            sqlSession.update("department.update", departmentDO);
            return true;
        } catch (Exception e) {
            logger.error("DepartmentDao update catch exception, departmentDO=" + JSON.toJSONString(departmentDO), e);
            return false;
        }
    }
}
