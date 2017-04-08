package com.alpha.dao.impl;

import com.alpha.dao.GasCardDao;
import com.alpha.domain.GasCardDO;
import com.alpha.query.GasCardQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("gasCardDao")
public class GasCardImpl implements GasCardDao {
    @Override
    public boolean insert(GasCardDO gasCardDO) {
        return false;
    }

    @Override
    public List<GasCardDO> query(GasCardQuery gasCardQuery) {
        return null;
    }

    @Override
    public boolean update(GasCardDO gasCardDO) {
        return false;
    }
}
