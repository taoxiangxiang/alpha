package com.alpha.manager;

import com.alpha.domain.MaintainDO;
import com.alpha.query.MaintainQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
public interface MaintainManager {

    boolean insert(MaintainDO maintainDO);

    List<MaintainDO> query(MaintainQuery maintainQuery);

    int count(MaintainQuery maintainQuery);

    boolean update(MaintainDO maintainDO);
}
