package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.dao.TeamDao;
import com.alpha.domain.PartnerDepartmentDO;
import com.alpha.domain.TeamDO;
import com.alpha.query.TeamQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/5/26.
 */
@Component("teamDao")
public class TeamDaoImpl implements TeamDao {

    private static final Logger logger = LoggerFactory.getLogger(TeamDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(TeamDO teamDO) {
        try {
            sqlSession.insert("team.insert", teamDO);
            return true;
        } catch (Exception e) {
            logger.error("TeamDao insert catch exception, teamDO=" + JSON.toJSONString(teamDO), e);
            return false;
        }
    }

    @Override
    public List<TeamDO> query(TeamQuery teamQuery) {
        try {
            return sqlSession.selectList("team.selectByPage", teamQuery);
        } catch (Exception e) {
            logger.error("TeamDao query catch exception, teamQuery=" + JSON.toJSONString(teamQuery), e);
            return null;
        }
    }

    @Override
    public int count(TeamQuery teamQuery) {
        try {
            return (Integer) sqlSession.selectOne("team.count", teamQuery);
        } catch (Exception e) {
            logger.error("TeamDao count catch exception, teamQuery=" + JSON.toJSONString(teamQuery), e);
            return 0;
        }
    }

    @Override
    public boolean invalid(int id) {
        try {
            sqlSession.update("team.update", id);
            return true;
        } catch (Exception e) {
            logger.error("TeamDao update catch exception, id=" + JSON.toJSONString(id), e);
            return false;
        }
    }
}
