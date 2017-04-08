package com.alpha.manager.impl;

import com.alpha.dao.GasCardDao;
import com.alpha.domain.GasCardDO;
import com.alpha.manager.GasCardManager;
import com.alpha.query.GasCardQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("gasCardManager")
public class GasCardManagerImpl implements GasCardManager {

    @Resource
    private GasCardDao gasCardDao;

    @Override
    public boolean insert(GasCardDO gasCardDO) {
        return gasCardDao.insert(gasCardDO);
    }

    @Override
    public List<GasCardDO> query(GasCardQuery gasCardQuery) {
        return gasCardDao.query(gasCardQuery);
    }

    @Override
    public boolean update(GasCardDO gasCardDO) {
        return gasCardDao.update(gasCardDO);
    }
}
