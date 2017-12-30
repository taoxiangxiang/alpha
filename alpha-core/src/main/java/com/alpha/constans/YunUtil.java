package com.alpha.constans;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
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
     * SMS_72765031：您有新的用车调派单号：${applicationId}，用车时间为：${useDate}, 预计回车时间：${predictBackDate}，联系人：${contacts}，联系人电话${contactsPhone},请和联系人联系后尽快发车。
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
        paramStringMap.put("vocation_BeginDate", CalendarUtil.toString(leaveDO.getStartDate(), "MM-dd HH:mm:ss"));
        paramStringMap.put("vocation_EndDate", CalendarUtil.toString(leaveDO.getEndDate(),"MM-dd HH:mm:ss"));
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

    public static boolean sendVehicleVerifyPass(VehicleApplicationDO vehicleApplicationDO, VehicleUseDO vehicleUseDO) {
        Map<String, Object> paramStringMap = new HashMap<String, Object>();
        paramStringMap.put("applicationId", vehicleApplicationDO.getApplicationNO());
        paramStringMap.put("driverName", vehicleUseDO.getDriverName());
        paramStringMap.put("number", vehicleUseDO.getDriverPhone());
        sendMsg(vehicleUseDO.getApplicantPhone(), JSON.toJSONString(paramStringMap), "SMS_63890293");

        Map<String, Object> paramStringMap2 = new HashMap<String, Object>();
        paramStringMap2.put("applicationId", vehicleApplicationDO.getApplicationNO());
        paramStringMap2.put("useDate", CalendarUtil.toString(vehicleApplicationDO.getUseDate(), "MM-dd HH:mm:ss"));
        paramStringMap2.put("predictBackDate", CalendarUtil.toString(vehicleApplicationDO.getPredictBackDate(), "MM-dd HH:mm:ss"));
        paramStringMap2.put("contacts", vehicleApplicationDO.getContacts());
        paramStringMap2.put("contactsPhone", vehicleApplicationDO.getContactsPhone());

        return sendMsg(vehicleUseDO.getDriverPhone(), JSON.toJSONString(paramStringMap2), "SMS_72765031");
    }

    private static boolean sendMsgOld(String phoneNumber, String paramString, String templateCode) {
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


    public static void main2(String args[]) {
//        String host = "http://jisuqgclwz.market.alicloudapi.com";
//        String path = "/illegal/query";
//        String method = "GET";
//        String appcode = "53b47f50df2a430fb84ac6a795dcd4fc";
//        Map<String, String> headers = new HashMap<String, String>();
//        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//        headers.put("Authorization", "APPCODE " + appcode);
//        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("carorg", "taizhou");
//        querys.put("engineno", "CRE093483");
//        querys.put("frameno", "WAUYGB4H4GN018022");
//        querys.put("iscity", "1");
//        querys.put("lsnum", "MUE005");//车牌剩余部分
//        querys.put("lsprefix", "苏");//车牌前缀
////        querys.put("lstype", "02");
//
//        try {
//            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
//            //获取response的body
//            System.out.print( EntityUtils.toString(response.getEntity()));
//        } catch (Exception e) {
//        }

        Map<String, Object> paramStringMap2 = new HashMap<String, Object>();
        paramStringMap2.put("applicationId", "1");
        paramStringMap2.put("useDate", CalendarUtil.toString(new Date(), "MM-dd HH:mm:ss")
                );
        paramStringMap2.put("contacts", "12");
        paramStringMap2.put("contactsPhone", "13");

         sendMsg("17091608626", JSON.toJSONString(paramStringMap2), "SMS_72820028");
    }

    private static boolean sendMsg(String phoneNumber, String paramString, String templateCode) {
        try {
            //设置超时时间-可自行调整
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //初始化ascClient需要的几个参数
            final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
            final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
            //替换成你的AK
            final String accessKeyId = "LTAIERdoz1Qj2jSE";//你的accessKeyId,参考本文档步骤2
            final String accessKeySecret = "xDmAx2X4PYNly5SMexhNnEmUaznfph";//你的accessKeySecret，参考本文档步骤2
            //初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName("车管系统");

            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
            request.setPhoneNumbers(phoneNumber);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam(paramString);
            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId("yourOutId");
            //请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
