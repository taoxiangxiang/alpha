package com.alpha.web.common;

import com.alibaba.citrus.service.pull.PullService;
import com.alibaba.citrus.util.StringEscapeUtil;
import com.alibaba.citrus.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by taoxiang on 2017/4/6.
 */
public class BaseAjaxModule extends BaseModule {

    protected void print(Object object) {
        String callback = StringEscapeUtil.escapeHtml(request.getParameter("callback"));
        String backvar = StringEscapeUtil.escapeHtml(request.getParameter("backvar"));
        String result = JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
        if (callback != null) {
            result = callback + "(" + result + ");";
        }
        if (backvar != null) {
            result = backvar + "=" + result;
        }
        print(result);
    }

    protected void downFile(String fileName, FileInputStream in ) throws IOException {
        this.writeFileByteHeader(fileName);
        try {
            while (true) {
                int readByte = in.read();
                if (readByte < 0) {
                    break;
                }
                this.writeFileByte(readByte);
            }
            this.writeFileByteClose();
        } finally {
            in.close();
        }
    }

    private void writeFileByteHeader(String fileName) throws IOException {
//        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/msword");
        fileName = new String(fileName.getBytes(),"ISO8859-1");
//        response.setContentType("application/octet-stream;charset=UTF-8");
//        fileName = new String(fileName.getBytes(),"UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
    }

    private void writeFileByte(int bytes) throws IOException {
        response.getOutputStream().write(bytes);
    }

    private void writeFileByteClose() throws IOException {
        response.getOutputStream().close();
    }

    /*
     * 导出word文件
     */
    protected void downWord(Map<String,Object> dataMap, String templateWord) throws IOException, TemplateException{
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(this.getClass(), "/template");//模板文件所在路径
        Template t = configuration.getTemplate(templateWord); //获取模板文件
        if (t == null) {
            return;
        }
        String savePath = session.getServletContext().getRealPath("/");
//        String savePath = "/Users/taoxiang/Desktop/";
        savePath = savePath.replace("alpha", "down");
        String fileName = "用车申请单_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION) + ".doc";
        File outFile = new File(savePath + fileName); //导出文件
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
        t.process(dataMap, out); //将填充数据填入模板文件并输出到目标文件
        //关闭流
        out.flush();
        out.close();
        FileInputStream in = new FileInputStream(savePath + fileName);
        downFile(fileName, in);
    }

    /*
     * 导出excle文件
     */
    protected void printExcel(XSSFWorkbook book, String fileName) throws IOException {
        response.setContentType("application/vnd.ms-excel;charset=ISO8859-1");
        fileName = fileName + ".xlsx";
        fileName = new String(fileName.getBytes(),"ISO8859-1");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        OutputStream outputStream = response.getOutputStream();
        book.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    protected boolean hasAuth(String authType) {
        if (StringUtil.isBlank(authType)) {
            return false;
        }
        String[] authArray = authType.split(",");
        StringBuilder sb = new StringBuilder();
        for (String auth : authArray) {
            if (SystemConstant.authTypeMap.containsKey(auth)) {
                return true;
            }
        }
        return false;
    }
}
