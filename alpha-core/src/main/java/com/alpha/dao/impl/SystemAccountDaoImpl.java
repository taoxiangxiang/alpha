package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.SystemAccountDao;
import com.alpha.domain.SystemAccountDO;
import com.alpha.query.SystemAccountQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/3/20.
 */
@Component("systemAccountDao")
public class SystemAccountDaoImpl implements SystemAccountDao {

    private static final Logger logger = LoggerFactory.getLogger(SystemAccountDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    public boolean insert(SystemAccountDO systemAccountDO) {
        try {
            sqlSession.insert("systemAccount.insert",systemAccountDO);
            return true;
        } catch (Exception e) {
            logger.error("UserDao insert catch exception, systemAccountDO=" + JSON.toJSONString(systemAccountDO), e);
            return false;
        }
    }

    public List<SystemAccountDO> query(SystemAccountQuery systemAccountQuery) {
        try {
            return sqlSession.selectList("systemAccount.selectByPage",systemAccountQuery);
        } catch (Exception e) {
            logger.error("UserDao query catch exception, systemAccountQuery=" + JSON.toJSONString(systemAccountQuery), e);
            return null;
        }
    }
}
