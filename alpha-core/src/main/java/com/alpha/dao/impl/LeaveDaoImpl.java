package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.constans.CalendarUtil;
import com.alpha.dao.LeaveDao;
import com.alpha.domain.LeaveDO;
import com.alpha.domain.MaintainDO;
import com.alpha.query.LeaveQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/29.
 */
@Component("leaveDao")
public class LeaveDaoImpl implements LeaveDao {

    private static final Logger logger = LoggerFactory.getLogger(LeaveDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(LeaveDO leaveDO) {
        try {
            sqlSession.insert("leave.insert", leaveDO);
            return true;
        } catch (Exception e) {
            logger.error("LeaveDao insert catch exception, leaveDO=" + JSON.toJSONString(leaveDO), e);
            return false;
        }
    }

    @Override
    public List<LeaveDO> query(LeaveQuery leaveQuery) {
        try {
            List<LeaveDO> list = sqlSession.selectList("leave.selectByPage", leaveQuery);
            if (list != null && list.size() > 0) {
                for (LeaveDO leaveDO : list) {
                    leaveDO.setApplicationNO("JQ" +
                            Integer.valueOf(CalendarUtil.toString(leaveDO.getGmtCreate(), CalendarUtil.DATE_FMT_5)) * 1000000 + leaveDO.getId());
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("LeaveDao query catch exception, leaveQuery=" + JSON.toJSONString(leaveQuery), e);
            return null;
        }
    }

    @Override
    public int count(LeaveQuery leaveQuery) {
        try {
            return (Integer)sqlSession.selectOne("leave.count", leaveQuery);
        } catch (Exception e) {
            logger.error("LeaveDao count catch exception, leaveQuery=" + JSON.toJSONString(leaveQuery), e);
            return 0;
        }
    }

    @Override
    public boolean update(LeaveDO leaveDO) {
        try {
            sqlSession.update("leave.update", leaveDO);
            return true;
        } catch (Exception e) {
            logger.error("LeaveDao update catch exception, leaveDO=" + JSON.toJSONString(leaveDO), e);
            return false;
        }
    }
}
