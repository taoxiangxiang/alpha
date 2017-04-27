package com.alpha.manager.impl;

import com.alpha.dao.MaintainDao;
import com.alpha.domain.MaintainDO;
import com.alpha.manager.MaintainManager;
import com.alpha.query.MaintainQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Component("maintainManager")
public class MaintainManagerImpl implements MaintainManager {

    @Resource
    private MaintainDao maintainDao;

    @Override
    public boolean insert(MaintainDO maintainDO) {
        return maintainDao.insert(maintainDO);
    }

    @Override
    public List<MaintainDO> query(MaintainQuery maintainQuery) {
        return maintainDao.query(maintainQuery);
    }

    @Override
    public int count(MaintainQuery maintainQuery) {
        return maintainDao.count(maintainQuery);
    }

    @Override
    public boolean update(MaintainDO maintainDO) {
        return maintainDao.update(maintainDO);
    }
}
