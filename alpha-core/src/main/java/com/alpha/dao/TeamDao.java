package com.alpha.dao;

import com.alpha.domain.TeamDO;
import com.alpha.query.TeamQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/5/26.
 */
public interface TeamDao {

    boolean insert(TeamDO teamDO);

    List<TeamDO> query(TeamQuery teamQuery);

    int count(TeamQuery teamQuery);

    boolean invalid(int id);
}
