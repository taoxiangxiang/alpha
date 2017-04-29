package com.alpha.manager.impl;

import com.alpha.dao.LeaveDao;
import com.alpha.domain.LeaveDO;
import com.alpha.manager.LeaveManager;
import com.alpha.query.LeaveQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/29.
 */
@Component("leaveManager")
public class LeaveManagerImpl implements LeaveManager {

    @Resource
    private LeaveDao leaveDao;

    @Override
    public boolean insert(LeaveDO leaveDO) {
        return leaveDao.insert(leaveDO);
    }

    @Override
    public List<LeaveDO> query(LeaveQuery leaveQuery) {
        return leaveDao.query(leaveQuery);
    }

    @Override
    public int count(LeaveQuery leaveQuery) {
        return leaveDao.count(leaveQuery);
    }

    @Override
    public boolean update(LeaveDO leaveDO) {
        return leaveDao.update(leaveDO);
    }
}
