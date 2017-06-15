package com.alpha.manager.impl;

import com.alpha.constans.YunUtil;
import com.alpha.dao.SystemAccountDao;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.SystemAccountQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taoxiang on 2017/3/29.
 */
@Component("systemAccountManager")
public class SystemAccountManagerImpl implements SystemAccountManager {

    @Resource
    private SystemAccountDao systemAccountDao;

    @Override
    public boolean insert(SystemAccountDO systemAccountDO) {
        if (systemAccountDao.insert(systemAccountDO)) {
            YunUtil.sendAccountOpenMsg(systemAccountDO);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(SystemAccountDO systemAccountDO) {
        return systemAccountDao.update(systemAccountDO);
    }

    @Override
    public List<SystemAccountDO> query(SystemAccountQuery systemAccountQuery) {
        return systemAccountDao.query(systemAccountQuery);
    }

    @Override
    public SystemAccountDO queryByNick(String nick) {
        SystemAccountQuery systemAccountQuery =  new SystemAccountQuery();
        systemAccountQuery.setNick(nick);
        List<SystemAccountDO> list = systemAccountDao.query(systemAccountQuery);
        return (list == null || list.size() == 0) ? null : list.get(0);
    }

    @Override
    public SystemAccountDO queryByCitizenId(String citizenId) {
        SystemAccountQuery systemAccountQuery =  new SystemAccountQuery();
        systemAccountQuery.setCitizenId(citizenId);
        List<SystemAccountDO> list = systemAccountDao.query(systemAccountQuery);
        return (list == null || list.size() == 0) ? null : list.get(0);
    }

    @Override
    public int count(SystemAccountQuery systemAccountQuery) {
        return systemAccountDao.count(systemAccountQuery);
    }
}
