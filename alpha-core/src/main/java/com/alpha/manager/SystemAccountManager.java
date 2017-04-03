package com.alpha.manager;

import com.alpha.domain.SystemAccountDO;
import com.alpha.query.SystemAccountQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/3/29.
 */
public interface SystemAccountManager {

    boolean insert(SystemAccountDO systemAccountDO);

    List<SystemAccountDO> query(SystemAccountQuery systemAccountQuery);
}
