package com.alpha.manager;

import com.alpha.domain.GasCardDO;
import com.alpha.query.GasCardQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public interface GasCardManager {

    boolean insert(GasCardDO gasCardDO);

    List<GasCardDO> query(GasCardQuery gasCardQuery);

    boolean update(GasCardDO gasCardDO);
}
