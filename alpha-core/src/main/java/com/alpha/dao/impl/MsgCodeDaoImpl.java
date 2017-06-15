package com.alpha.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.constans.CalendarUtil;
import com.alpha.dao.MsgCodeDao;
import com.alpha.domain.MsgCodeDO;
import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by taoxiang on 2017/6/11.
 */
@Component("msgCodeDao")
public class MsgCodeDaoImpl implements MsgCodeDao {

    private static final Logger logger = LoggerFactory.getLogger(MsgCodeDaoImpl.class);

    @Resource
    private SqlSessionTemplate sqlSession;

    @Override
    public boolean insert(MsgCodeDO msgCodeDO) {
        try {
            sqlSession.insert("msgCode.insert", msgCodeDO);
            return true;
        } catch (Exception e) {
            logger.error("MsgCodeDao insert catch exception, msgCodeDO=" + JSON.toJSONString(msgCodeDO), e);
            return false;
        }
    }

    @Override
    public MsgCodeDO queryRecentOneByPhone(String phoneNumber) {
        try {
            Long beforeDate = new Date().getTime() - 10L * 60L * 1000L;
            List<MsgCodeDO> list = queryByPhone(phoneNumber, new Date(beforeDate));
            return (list == null || list.size() == 0) ? null : list.get(0);
        } catch (Exception e) {
            logger.error("MsgCodeDao queryRecentOneByPhone catch exception, phoneNumber=" + JSON.toJSONString(phoneNumber), e);
            return null;
        }
    }

    @Override
    public List<MsgCodeDO> queryByPhone(String phoneNumber, Date startDate) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("phoneNumber", phoneNumber);
            map.put("startDate", startDate);
            return sqlSession.selectList("msgCode.query", map);
        } catch (Exception e) {
            logger.error("MsgCodeDao queryByPhone catch exception, phoneNumber="
                    + phoneNumber + ",startDate=" + CalendarUtil.toString(startDate, CalendarUtil.DATE_FMT_2), e);
            return null;
        }
    }
}
