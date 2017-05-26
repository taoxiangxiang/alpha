package com.alpha.web.module.screen.api.file;

import java.util.*;

import com.alpha.constans.CalendarUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;

/**
 * Created by taoxiang on 2017/5/14.
 */
public class WordTest {

    private Configuration configuration = null;

    public WordTest(){
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
    }

    public static void main(String[] args) {
        WordTest test = new WordTest();
        test.createWord();
    }

    private void createWord(){
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            getData(dataMap);
//        configuration.setClassForTemplateLoading(this.getClass(), "/template");//模板文件所在路径
//        Template t=null;
//        try {
//            t = configuration.getTemplate("vehicleApplication.xml"); //获取模板文件
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (t == null) {
//            return;
//        }
//        File outFile = new File("/Users/taoxiang/Desktop/vehicleApplication.doc"); //导出文件
//        Writer out = null;
//        try {
//            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
//        } catch (FileNotFoundException e1) {
//            e1.printStackTrace();
//        }
//
//        try {
//            t.process(dataMap, out); //将填充数据填入模板文件并输出到目标文件
//        } catch (TemplateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("UTF-8");
            configuration.setClassForTemplateLoading(this.getClass(), "/template");//模板文件所在路径
            Template t = configuration.getTemplate("vehicleApplication.xml"); //获取模板文件
            if (t == null) {
                return;
            }
//        String savePath = session.getServletContext().getRealPath("/");
            String savePath = "/Users/taoxiang/Desktop/";
            savePath = savePath.replace("alpha", "down");
            String fileName = "用车申请单_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION) + ".doc";
            File outFile = new File(savePath + fileName); //导出文件
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            t.process(dataMap, out); //将填充数据填入模板文件并输出到目标文件
            //关闭流
            out.flush();
            out.close();
            FileInputStream in = new FileInputStream(savePath + fileName);
        } catch (Exception e) {

        }
    }

    private void getData(Map<String, Object> dataMap) {
        dataMap.put("id", "1");
        dataMap.put("gmtCreate", "2016");
        dataMap.put("applicationDepartment", "1");
        dataMap.put("applicationType", "1");
        dataMap.put("applicant", "1");
        dataMap.put("applicantPhone", "1");
        dataMap.put("startPlace", "1");
        dataMap.put("endPlace", "1");
        dataMap.put("vehicleType", "1");
        dataMap.put("personNumber", "1");
        dataMap.put("useDate", "1");
        dataMap.put("predictBackDate", "3");
        dataMap.put("applicationType", "6");
        dataMap.put("applicationReason", "lc");
    }
}
