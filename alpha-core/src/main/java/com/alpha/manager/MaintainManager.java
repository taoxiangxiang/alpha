package com.alpha.manager;

import com.alpha.domain.MaintainDO;
import com.alpha.domain.MaintainSumDO;
import com.alpha.query.MaintainQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
public interface MaintainManager {

    boolean insert(MaintainDO maintainDO);

    List<MaintainDO> query(MaintainQuery maintainQuery);

    MaintainDO queryById(int id);

    int count(MaintainQuery maintainQuery);

    boolean update(MaintainDO maintainDO);

    boolean sendMaintain(MaintainDO maintainDO) throws Exception ;

    boolean pickUpMaintain(MaintainDO maintainDO) throws Exception ;

    List<MaintainSumDO> queryGroupByVehicle(MaintainQuery maintainQuery);

    int countGroupByVehicle(MaintainQuery maintainQuery);
}
