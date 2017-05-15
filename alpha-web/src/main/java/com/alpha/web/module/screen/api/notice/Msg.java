package com.alpha.web.module.screen.api.notice;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.HttpUtils;
import com.alpha.web.common.BaseAjaxModule;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taoxiang on 2017/4/29.
 */
public class Msg extends BaseAjaxModule {

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("vehicleNO") String vehicleNO,
                        @Param("id") Integer id, Context context) {
//        sendMsg(phoneNumber, templateCode);
    }

//    private void sendMsg(String phoneNumber, String templateCode) {
    public static void main(String[] args) {
        String host = "http://sms.market.alicloudapi.com";
        String path = "/singleSendSms";
        String method = "GET";
        String appcode = "53b47f50df2a430fb84ac6a795dcd4fc";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ParamString", "{\"applicationId\":\"123456\",\"driverName\":\"123456\",\"number\":\"123456\"}");
        querys.put("RecNum", "13770613348"); //目标手机号,多条记录可以英文逗号分隔
        querys.put("SignName", "车管系统"); //签名名称
        querys.put("TemplateCode", "SMS_63890293");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        String host = "http://jisuqgclwz.market.alicloudapi.com";
//        String path = "/illegal/query";
//        String method = "GET";
//        String appcode = "53b47f50df2a430fb84ac6a795dcd4fc";
//        Map<String, String> headers = new HashMap<String, String>();
//        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//        headers.put("Authorization", "APPCODE " + appcode);
//        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("carorg", "taizhou");
//        querys.put("engineno", "058911");
//        querys.put("frameno", "LSVET69F9A2560688");
//        querys.put("iscity", "1");
//        querys.put("lsnum", "MV6301");//车牌剩余部分
//        querys.put("lsprefix", "苏");//车牌前缀
////        querys.put("lstype", "02");
//
//        try {
//            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
//            //获取response的body
//            System.out.println(EntityUtils.toString(response.getEntity()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
