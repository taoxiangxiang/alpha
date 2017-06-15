package com.alpha.constans;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alpha.domain.*;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.Date;
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

    public static boolean sendCodeMsg(String phoneNumber,String code) {
        Map<String, Object> paramStringMap = new HashMap<String, Object>();
        paramStringMap.put("code",  code);
        return sendMsg(phoneNumber, JSON.toJSONString(paramStringMap), "SMS_66990041");
    }

    public static boolean sendAccountOpenMsg(SystemAccountDO systemAccountDO) {
        Map<String, Object> paramStringMap = new HashMap<String, Object>();
        paramStringMap.put("accountId", systemAccountDO.getName());
        paramStringMap.put("accountCode", systemAccountDO.getCitizenId().substring(12));
        return sendMsg(systemAccountDO.getMobilePhone(), JSON.toJSONString(paramStringMap), "SMS_64655038");
    }

    public static boolean sendLeaveVerifyReject(LeaveDO leaveDO) {
        Map<String, Object> paramStringMap = new HashMap<String, Object>();
        paramStringMap.put("vocationId", leaveDO.getApplicationNO());
        return sendMsg(leaveDO.getMobilePhone(), JSON.toJSONString(paramStringMap), "SMS_64645041");
    }

    public static boolean sendLeaveVerifyPass(LeaveDO leaveDO) {
        Map<String, Object> paramStringMap = new HashMap<String, Object>();
        paramStringMap.put("vocationId", leaveDO.getApplicationNO());
        paramStringMap.put("vocation_BeginDate", leaveDO.getStartDate());
        paramStringMap.put("vocation_EndDate", leaveDO.getEndDate());
        return sendMsg(leaveDO.getMobilePhone(), JSON.toJSONString(paramStringMap), "SMS_64650070");
    }

    public static boolean sendMaintainVerifyReject(MaintainDO maintainDO) {
        Map<String, Object> paramStringMap = new HashMap<String, Object>();
        paramStringMap.put("maintainId", maintainDO.getApplicationNO());
        return sendMsg(maintainDO.getApplicantPhone(), JSON.toJSONString(paramStringMap), "SMS_64625027");
    }

    public static boolean sendMaintainVerifyPass(MaintainDO maintainDO) {
        Map<String, Object> paramStringMap = new HashMap<String, Object>();
        paramStringMap.put("maintainId", maintainDO.getApplicationNO());
        paramStringMap.put("maintain_address", maintainDO.getMaintainAddress());
        paramStringMap.put("number", maintainDO.getMaintainPhone());
        return sendMsg(maintainDO.getApplicantPhone(), JSON.toJSONString(paramStringMap), "SMS_64585006");
    }

    public static boolean sendVehicleVerifyReject(VehicleApplicationDO vehicleApplicationDO) {
        Map<String, Object> paramStringMap = new HashMap<String, Object>();
        paramStringMap.put("applicationId", vehicleApplicationDO.getApplicationNO());
        return sendMsg(vehicleApplicationDO.getApplicantPhone(), JSON.toJSONString(paramStringMap), "SMS_64480072");
    }

    public static boolean sendVehicleVerifyPass(VehicleUseDO vehicleUseDO) {
        Map<String, Object> paramStringMap = new HashMap<String, Object>();
        paramStringMap.put("applicationId", vehicleUseDO.getApplicationNO());
        paramStringMap.put("driverName", vehicleUseDO.getDriverName());
        paramStringMap.put("number", vehicleUseDO.getDriverPhone());
        return sendMsg(vehicleUseDO.getApplicantPhone(), JSON.toJSONString(paramStringMap), "SMS_63890293");
    }

    private static boolean sendMsg(String phoneNumber, String paramString, String templateCode) {
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
            return Boolean.valueOf(String.valueOf(jsonObject.get("success")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static String queryVehicleIllegal(VehicleDO vehicleDO, String diQu) {
        String host = "http://jisuqgclwz.market.alicloudapi.com";
        String path = "/illegal/query";
        String method = "GET";
        String appcode = "53b47f50df2a430fb84ac6a795dcd4fc";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("carorg", diQu);
        querys.put("engineno", vehicleDO.getEngineNO());
        querys.put("frameno", vehicleDO.getVin());
        querys.put("iscity", "1");
        querys.put("lsnum", vehicleDO.getVehicleNO().substring(1,vehicleDO.getVehicleNO().length()));//车牌剩余部分
        querys.put("lsprefix", vehicleDO.getVehicleNO().substring(0,1));//车牌前缀
//        querys.put("lstype", "02");

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //获取response的body
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            return null;
        }
    }

    public static String getRandNum(int charCount) {
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

    public static void main(String args[]) {
        String fileName = "a.doc";
        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
        System.out.println(prefix);
    }
}
