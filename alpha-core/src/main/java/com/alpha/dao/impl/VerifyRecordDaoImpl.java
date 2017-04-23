package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.VerifyRecordDao;
import com.alpha.domain.VerifyRecordDO;
import com.alpha.query.VerifyRecordQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/6.
 */
@Component("verifyRecordDao")
public class VerifyRecordDaoImpl implements VerifyRecordDao {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(VerifyRecordDO verifyRecordDO) {
        try {
            sqlSession.insert("verifyRecord.insert", verifyRecordDO);
            return true;
        } catch (Exception e) {
            logger.error("VerifyRecordDao insert catch exception, verifyRecordDO=" + JSON.toJSONString(verifyRecordDO), e);
            return false;
        }
    }

    @Override
    public List<VerifyRecordDO> query(VerifyRecordQuery verifyRecordQuery) {
        try {
            return sqlSession.selectList("verifyRecord.selectByPage", verifyRecordQuery);
        } catch (Exception e) {
            logger.error("VerifyRecordDao query catch exception, verifyRecordQuery=" + JSON.toJSONString(verifyRecordQuery), e);
            return null;
        }
    }

    @Override
    public int count(VerifyRecordQuery verifyRecordQuery) {
        try {
            return (Integer)sqlSession.selectOne("verifyRecord.count", verifyRecordQuery);
        } catch (Exception e) {
            logger.error("VerifyRecordDao count catch exception, verifyRecordQuery=" + JSON.toJSONString(verifyRecordQuery), e);
            return 0;
        }
    }
}
