package com.alpha.dao;

import com.alpha.domain.LeaveDO;
import com.alpha.query.LeaveQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/29.
 */
public interface LeaveDao {

    boolean insert(LeaveDO leaveDO);

    List<LeaveDO> query(LeaveQuery leaveQuery);

    int count(LeaveQuery leaveQuery);

    boolean update(LeaveDO leaveDO);

}
