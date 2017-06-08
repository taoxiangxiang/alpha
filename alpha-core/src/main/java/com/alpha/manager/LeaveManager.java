package com.alpha.manager;

import com.alpha.domain.LeaveDO;
import com.alpha.domain.LeaveSumDO;
import com.alpha.query.LeaveQuery;

import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/29.
 */
public interface LeaveManager {

    boolean insert(LeaveDO leaveDO);

    List<LeaveDO> query(LeaveQuery leaveQuery);

    int count(LeaveQuery leaveQuery);

    boolean update(LeaveDO leaveDO);

    List<LeaveSumDO> getSumList(List<LeaveDO> leaveDOList, Date start, Date end);
}
