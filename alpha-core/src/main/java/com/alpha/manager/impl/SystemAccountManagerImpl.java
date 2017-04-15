package com.alpha.manager.impl;

import com.alpha.dao.SystemAccountDao;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.SystemAccountQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/3/29.
 */
@Component("systemAccountManager")
public class SystemAccountManagerImpl implements SystemAccountManager {

    @Resource
    private SystemAccountDao systemAccountDao;

    @Override
    public boolean insert(SystemAccountDO systemAccountDO) {
        return systemAccountDao.insert(systemAccountDO);
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
    public int count(SystemAccountQuery systemAccountQuery) {
        return systemAccountDao.count(systemAccountQuery);
    }
}
