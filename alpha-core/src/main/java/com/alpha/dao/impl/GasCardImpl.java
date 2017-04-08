package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.GasCardDao;
import com.alpha.domain.GasCardDO;
import com.alpha.query.GasCardQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("gasCardDao")
public class GasCardImpl implements GasCardDao {

    private static final Logger logger = LoggerFactory.getLogger(GasCardImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(GasCardDO gasCardDO) {
        try {
            sqlSession.insert("gasCard.insert", gasCardDO);
            return true;
        } catch (Exception e) {
            logger.error("GasCardDao insert catch exception, gasCardDO=" + JSON.toJSONString(gasCardDO), e);
            return false;
        }
    }

    @Override
    public List<GasCardDO> query(GasCardQuery gasCardQuery) {
        try {
            return sqlSession.selectList("gasCard.selectByPage", gasCardQuery);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            logger.error("GasCardDao query catch exception, gasCardQuery=" + JSON.toJSONString(gasCardQuery), e);
            return null;
        }
    }

    @Override
    public boolean update(GasCardDO gasCardDO) {
        try {
            sqlSession.update("gasCard.update", gasCardDO);
            return true;
        } catch (Exception e) {
            logger.error("GasCardDao update catch exception, gasCardDO=" + JSON.toJSONString(gasCardDO), e);
            return false;
        }
    }
}
