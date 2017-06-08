package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.constans.CalendarUtil;
import com.alpha.dao.MaintainDao;
import com.alpha.domain.MaintainDO;
import com.alpha.domain.MaintainSumDO;
import com.alpha.query.MaintainQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Component("maintainDao")
public class MaintainDaoImpl implements MaintainDao {

    private static final Logger logger = LoggerFactory.getLogger(MaintainDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(MaintainDO maintainDO) {
        try {
            sqlSession.insert("maintain.insert", maintainDO);
            return true;
        } catch (Exception e) {
            logger.error("MaintainDao insert catch exception, maintainDO=" + JSON.toJSONString(maintainDO), e);
            return false;
        }
    }

    @Override
    public List<MaintainDO> query(MaintainQuery maintainQuery) {
        try {
            List<MaintainDO> list = sqlSession.selectList("maintain.selectByPage", maintainQuery);
            if (list != null && list.size() > 0) {
                for (MaintainDO maintainDO : list) {
                    maintainDO.setApplicationNO("WB" +
                            Integer.valueOf(CalendarUtil.toString(maintainDO.getGmtCreate(), CalendarUtil.DATE_FMT_5)) * 1000000 + maintainDO.getId());
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("MaintainDao query catch exception, departmentQuery=" + JSON.toJSONString(maintainQuery), e);
            return null;
        }
    }

    @Override
    public int count(MaintainQuery maintainQuery) {
        try {
            return (Integer)sqlSession.selectOne("maintain.count", maintainQuery);
        } catch (Exception e) {
            logger.error("MaintainDao count catch exception, departmentQuery=" + JSON.toJSONString(maintainQuery), e);
            return 0;
        }
    }

    @Override
    public boolean update(MaintainDO maintainDO) {
        try {
            sqlSession.update("maintain.update", maintainDO);
            return true;
        } catch (Exception e) {
            logger.error("MaintainDao update catch exception, maintainDO=" + JSON.toJSONString(maintainDO), e);
            return false;
        }
    }

    @Override
    public List<MaintainSumDO> queryGroupByVehicle(MaintainQuery maintainQuery) {
        try {
            return sqlSession.selectList("maintain.selectSumByPage", maintainQuery);
        } catch (Exception e) {
            logger.error("MaintainDao queryGroupByVehicle catch exception, departmentQuery=" + JSON.toJSONString(maintainQuery), e);
            return null;
        }
    }

    @Override
    public int countGroupByVehicle(MaintainQuery maintainQuery) {
        try {
            return (Integer)sqlSession.selectOne("maintain.countSum", maintainQuery);
        } catch (Exception e) {
            logger.error("MaintainDao countGroupByVehicle catch exception, departmentQuery=" + JSON.toJSONString(maintainQuery), e);
            return 0;
        }
    }
}
