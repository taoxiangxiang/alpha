package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.YunUtil;
import com.alpha.domain.SystemAccountDO;
import com.alpha.query.SystemAccountQuery;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by taoxiang on 2017/3/29.
 */
public class UserManagerTest extends ITestBase {

    @Resource
    private SystemAccountManager systemAccountManager;
    @Resource
    private MsgManager msgManager;

    @Test
    public void testMsg() {
        msgManager.sendCodeMsg("17091608626");
    }

    @Test
    public void testInsert() throws NoSuchAlgorithmException, UnsupportedEncodingException{
        SystemAccountDO systemAccountDO = new SystemAccountDO();
        systemAccountDO.setName("admin");
        systemAccountDO.setNick("admin");
        String citizenId = "340223199005154610";
        systemAccountDO.setCitizenId(citizenId);
        systemAccountDO.setMobilePhone("17091608626");
        //身份证后六位
        systemAccountDO.setPassword(encoderByMd5(citizenId.substring(12)));
//        systemAccountDO.setPassword("1234");
//        systemAccountDO.setCitizenId("340223");
//        systemAccountDO.setBirth("19901010");
//        systemAccountDO.setEthnicGroup("汉族");
//        systemAccountDO.setNativePlace("浙江杭州");
//        systemAccountDO.setEducation("本科");
//        systemAccountDO.setTelNumber("0553-8261069");
//        systemAccountDO.setMobilePhone("17891608627");
//        systemAccountDO.setMailbox("zhouxingxing@163.com");
//        systemAccountDO.setAddress("杭州理工大学");
//        systemAccountDO.setHireDate("2007-10-01");
//        systemAccountDO.setPosition("系统职位");
        systemAccountDO.setDepartment(citizenId.substring(12));
        systemAccountDO.setAuthType("ALL");
//        systemAccountDO.setPicUrl("picUrl");
//        systemAccountDO.setAttribute("attribute");
        System.out.println(systemAccountManager.insert(systemAccountDO));
//        YunUtil.sendCodeMsg("17091608626");
    }

    @Test
    public void testQuery() {
        SystemAccountQuery systemAccountQuery = new SystemAccountQuery();
        systemAccountQuery.setName("admin");
        List<SystemAccountDO> list = systemAccountManager.query(systemAccountQuery);
        System.out.println("list=" + JSON.toJSONString(list));
    }

    protected String encoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        return base64en.encode(md5.digest(str.getBytes("utf-8")));
    }
}
