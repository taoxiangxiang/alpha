package com.alpha.manager.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.constans.SystemConstant;
import com.alpha.constans.YunUtil;
import com.alpha.dao.SystemAccountDao;
import com.alpha.domain.DriverDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.DriverManager;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.SystemAccountQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taoxiang on 2017/3/29.
 */
@Component("systemAccountManager")
public class SystemAccountManagerImpl implements SystemAccountManager {

    @Resource
    private SystemAccountDao systemAccountDao;
    @Resource
    private DriverManager driverManager;

    @Override
    public boolean insert(SystemAccountDO systemAccountDO) {
        if (systemAccountDao.insert(systemAccountDO)) {
            YunUtil.sendAccountOpenMsg(systemAccountDO);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(SystemAccountDO systemAccountDO){
        return systemAccountDao.update(systemAccountDO);
    }

    @Override
    @Transactional(rollbackForClassName="Exception")
    public boolean updateInfo(SystemAccountDO systemAccountDO) throws Exception{
        SystemAccountQuery systemAccountQuery = new SystemAccountQuery();
        systemAccountQuery.setId(systemAccountDO.getId());
        List<SystemAccountDO> list = query(systemAccountQuery);
        if (list != null) {
            SystemAccountDO systemAccountDOInDB = list.get(0);
            if (SystemConstant.USER_TYPE_DRIVER.equals(systemAccountDOInDB.getType())) {
                int driverId = Integer.valueOf(String.valueOf(JSON.parseObject(systemAccountDOInDB.getAttribute()).get("driverId")));
                DriverDO driverDO = new DriverDO();
                driverDO.setId(driverId);
                driverDO.setName(systemAccountDO.getName());
                driverDO.setSex(systemAccountDO.getSex());
                driverDO.setCitizenId(systemAccountDO.getCitizenId());
                driverDO.setBirth(systemAccountDO.getBirth());
                driverDO.setEthnicGroup(systemAccountDO.getEthnicGroup());
                driverDO.setNativePlace(systemAccountDO.getNativePlace());
                driverDO.setEducation(systemAccountDO.getEducation());
                driverDO.setMobilePhone(systemAccountDO.getMobilePhone());
                driverDO.setMailbox(systemAccountDO.getMailbox());
                driverDO.setAddress(systemAccountDO.getAddress());
                driverDO.setPersonUrl(systemAccountDO.getPicUrl());
                if (!driverManager.update(driverDO)) {
                    throw new Exception("数据库查询失败");
                }
            }
        }
        return systemAccountDao.update(systemAccountDO);
    }

    @Override
    public List<SystemAccountDO> query(SystemAccountQuery systemAccountQuery) {
        return systemAccountDao.query(systemAccountQuery);
    }

    @Override
    public SystemAccountDO queryByNick(String nick) {
        SystemAccountQuery systemAccountQuery =  new SystemAccountQuery();
        systemAccountQuery.setNick(nick);
        List<SystemAccountDO> list = systemAccountDao.query(systemAccountQuery);
        return (list == null || list.size() == 0) ? null : list.get(0);
    }

    @Override
    public SystemAccountDO queryByCitizenId(String citizenId) {
        SystemAccountQuery systemAccountQuery =  new SystemAccountQuery();
        systemAccountQuery.setCitizenId(citizenId);
        List<SystemAccountDO> list = systemAccountDao.query(systemAccountQuery);
        return (list == null || list.size() == 0) ? null : list.get(0);
    }

    @Override
    public int count(SystemAccountQuery systemAccountQuery) {
        return systemAccountDao.count(systemAccountQuery);
    }
}
