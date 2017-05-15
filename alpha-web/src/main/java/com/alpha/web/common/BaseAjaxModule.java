package com.alpha.web.common;

import com.alibaba.citrus.service.pull.PullService;
import com.alibaba.citrus.util.StringEscapeUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by taoxiang on 2017/4/6.
 */
public class BaseAjaxModule extends BaseModule {

    protected void print(Object object) {
        String callback = StringEscapeUtil.escapeHtml(request.getParameter("callback"));
        String backvar = StringEscapeUtil.escapeHtml(request.getParameter("backvar"));
        String result = JSON.toJSONString(object);
        if (callback != null) {
            result = callback + "(" + result + ");";
        }
        if (backvar != null) {
            result = backvar + "=" + result;
        }
        print(result);
    }

    protected void downFile(String fileUrl, FileInputStream in ) throws IOException {
        this.writeFileByteHeader(fileUrl);
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
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        fileName = new String(fileName.getBytes(),"ISO8859-1");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
    }

    private void writeFileByte(int bytes) throws IOException {
        response.getOutputStream().write(bytes);
    }

    private void writeFileByteClose() throws IOException {
        response.getOutputStream().close();
    }

}
