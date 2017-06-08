package com.alpha.dao;

import com.alpha.domain.MaintainDO;
import com.alpha.domain.MaintainSumDO;
import com.alpha.query.MaintainQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
public interface MaintainDao {

    boolean insert(MaintainDO maintainDO);

    List<MaintainDO> query(MaintainQuery maintainQuery);

    int count(MaintainQuery maintainQuery);

    boolean update(MaintainDO maintainDO);

    List<MaintainSumDO> queryGroupByVehicle(MaintainQuery maintainQuery);

    int countGroupByVehicle(MaintainQuery maintainQuery);
}
