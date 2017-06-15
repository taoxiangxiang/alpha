package com.alpha.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.alpha.constans.SystemConstant;
import com.alpha.dao.DriverDao;
import com.alpha.domain.DriverDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.DriverManager;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.DriverQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("driverManager")
public class DriverManagerImpl implements DriverManager {

    @Resource
    private DriverDao driverDao;
    @Resource
    private SystemAccountManager systemAccountManager;

    @Override
    @Transactional(rollbackForClassName="Exception")
    public boolean insert(DriverDO driverDO) throws Exception{

        SystemAccountDO systemAccountDO = new SystemAccountDO();
        systemAccountDO.setName(driverDO.getName());
        systemAccountDO.setNick(driverDO.getName() + (systemAccountManager.queryByNick(driverDO.getName()) == null ? "" : "_1"));
        systemAccountDO.setSex(driverDO.getSex());
        systemAccountDO.setCitizenId(driverDO.getCitizenId());
        //身份证后六位
        systemAccountDO.setPassword(encoderByMd5(driverDO.getCitizenId().substring(12)));
        systemAccountDO.setBirth(driverDO.getBirth());
        systemAccountDO.setEthnicGroup(driverDO.getEthnicGroup());
        systemAccountDO.setNativePlace(driverDO.getNativePlace());
        systemAccountDO.setEducation(driverDO.getEducation());
        systemAccountDO.setTelNumber(driverDO.getMobilePhone());
        systemAccountDO.setMobilePhone(driverDO.getMobilePhone());
        systemAccountDO.setMailbox(driverDO.getMailbox());
        systemAccountDO.setAddress(driverDO.getAddress());
        systemAccountDO.setHireDate(null);
        systemAccountDO.setPosition(null);
        systemAccountDO.setDepartment(null);
        systemAccountDO.setPicUrl(driverDO.getPersonUrl());
        systemAccountDO.setStatus(SystemConstant.ACCOUNT_ON_LINE);
        systemAccountDO.setType(SystemConstant.USER_TYPE_DRIVER);
        if (!driverDao.insert(driverDO)) {
            return false;
        }
        DriverDO driverDOInDB = queryByCitizenId(driverDO.getCitizenId());
        if (driverDOInDB == null) {
            throw new Exception("数据库查询失败");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("driverId", driverDOInDB.getId());
        systemAccountDO.setAttribute(jsonObject.toJSONString());
        if (!systemAccountManager.insert(systemAccountDO)) {
            throw new Exception("数据库写入失败");
        }
        return true;
    }

    private String encoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        return base64en.encode(md5.digest(str.getBytes("utf-8")));
    }

    @Override
    public List<DriverDO> query(DriverQuery driverQuery) {
        return driverDao.query(driverQuery);
    }

    @Override
    public DriverDO queryById(int id) {
        DriverQuery driverQuery = new DriverQuery();
        driverQuery.setId(id);
        List<DriverDO> driverDOList = driverDao.query(driverQuery);
        return (driverDOList == null || driverDOList.size() == 0 ) ? null : driverDOList.get(0);
    }

    @Override
    public DriverDO queryByCitizenId(String citizenId) {
        DriverQuery driverQuery = new DriverQuery();
        driverQuery.setCitizenId(citizenId);
        List<DriverDO> driverDOList = driverDao.query(driverQuery);
        return (driverDOList == null || driverDOList.size() == 0 ) ? null : driverDOList.get(0);
    }

    @Override
    public int count(DriverQuery driverQuery) {
        return driverDao.count(driverQuery);
    }

    @Override
    public boolean update(DriverDO driverDO) {
        return driverDao.update(driverDO);
    }
}
