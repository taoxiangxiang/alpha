package com.alpha.dao;

import com.alpha.domain.SystemAccountDO;
import com.alpha.query.SystemAccountQuery;
import java.util.List;

/**
 * Created by taoxiang on 2017/3/20.
 */
public interface SystemAccountDao {

    boolean insert(SystemAccountDO systemAccountDO);

    List<SystemAccountDO> query(SystemAccountQuery systemAccountQuery);

    int count(SystemAccountQuery systemAccountQuery);

    boolean update(SystemAccountDO systemAccountDO);
}
