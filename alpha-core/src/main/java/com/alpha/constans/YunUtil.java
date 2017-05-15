package com.alpha.constans;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alpha.domain.SystemAccountDO;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by taoxiang on 2017/5/7.
 */
public class YunUtil {

    /**
     * SMS_66990041: 您的短信验证码是${code},有效期10分钟。
     * SMS_64655038: 您的用户名${accountId}已经申请成功，请使用密码${accountCode}登录系统并前往系统设置/用户管理进行默认密码修改。
     * SMS_64645041: 您的病事假申请单${vocationId}已经被驳回，请登录系统查看或联系车管中心。
     * SMS_64650070: 您的病事假申请单${vocationId}已经通过审核，时间为${vocation_BeginDate}至${vocation_EndDate}。
     * SMS_64625027: 您的车辆维修保养申请单${maintainId}已经被驳回，请登录系统查看或联系车管中心。
     * SMS_64585006: 您的车辆维修保养申请单${maintainId}已经通过审核，请至${maintain_address}进行维修保养，联系电话${number}。
     * SMS_64480072: 您的车辆使用申请单${applicationId}被驳回，请登录系统查看或联系车管中心。
     * SMS_63890293: 您的车辆使用申请单${applicationId}已经通过审核，请联系您的司机${driverName}，联系电话${number}
     */

    public static boolean sendCodeMsg(String phoneNumber) {
        Map<String, Object> paramStringMap = new HashMap<String, Object>();
        String code = "5";
        System.out.println(code);
        paramStringMap.put("code",  code);
        return sendCodeMsg(phoneNumber, JSON.toJSONString(paramStringMap), "SMS_66990041");
    }

    public static boolean sendAccountOpenMsg(SystemAccountDO systemAccountDO) {
        Map<String, Object> paramStringMap = new HashMap<String, Object>();
        paramStringMap.put("accountId", systemAccountDO.getName());
        paramStringMap.put("accountCode", systemAccountDO.getCitizenId().substring(12));
        return sendCodeMsg(systemAccountDO.getMobilePhone(), JSON.toJSONString(paramStringMap), "SMS_64655038");
    }

    private static boolean sendCodeMsg(String phoneNumber, String paramString, String templateCode) {
        String host = "http://sms.market.alicloudapi.com";
        String path = "/singleSendSms";
        String method = "GET";
        String appcode = "53b47f50df2a430fb84ac6a795dcd4fc";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ParamString", paramString);
        querys.put("RecNum", phoneNumber); //目标手机号,多条记录可以英文逗号分隔
        querys.put("SignName", "车管系统"); //签名名称
        querys.put("TemplateCode", templateCode);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //获取response的body
            JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(response.getEntity()));
            System.out.println("http=" + EntityUtils.toString(response.getEntity()));
            boolean res = Boolean.valueOf(String.valueOf(jsonObject.get("success")));
            System.out.println("send result=" + res + ", http=" + EntityUtils.toString(response.getEntity()));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void main1(String[] args) {
        String host = "http://jisuqgclwz.market.alicloudapi.com";
        String path = "/illegal/query";
        String method = "GET";
        String appcode = "53b47f50df2a430fb84ac6a795dcd4fc";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("carorg", "taizhou");
        querys.put("engineno", "058911");
        querys.put("frameno", "LSVET69F9A2560688");
        querys.put("iscity", "1");
        querys.put("lsnum", "MV6301");//车牌剩余部分
        querys.put("lsprefix", "苏");//车牌前缀
//        querys.put("lstype", "02");

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }

    private static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }
}
