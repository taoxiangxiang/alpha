package com.alpha.dao;

import com.alpha.domain.GasCardDO;
import com.alpha.query.GasCardQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public interface GasCardDao {

    boolean insert(GasCardDO gasCardDO);

    List<GasCardDO> query(GasCardQuery gasCardQuery);

    int count(GasCardQuery gasCardQuery);

    boolean update(GasCardDO gasCardDO);
}
