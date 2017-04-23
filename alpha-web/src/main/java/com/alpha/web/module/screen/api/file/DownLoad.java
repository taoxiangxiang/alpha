package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import java.io.*;

/**
 * Created by taoxiang on 2017/4/23.
 */
public class DownLoad extends BaseAjaxModule {

    public void execute(@Param("fileUrl") String fileUrl, Context context) {
        Result<String> result = new Result<String>();
        //设置响应头，控制浏览器下载该文件
//        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
        //读取要下载的文件，保存到文件输入流
        FileInputStream in = null;
        try {
            String savePath = session.getServletContext().getRealPath("/") + "file/";
            in = new FileInputStream(savePath + fileUrl);
        } catch (FileNotFoundException e) {
            result.setErrMsg("文件不存在");
            print(result);
            return;
        }
        //创建输出流
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            //创建缓冲区
            byte buffer[] = new byte[1024];
            int len = 0;
            //循环将输入流中的内容读取到缓冲区当中
            while((len = in.read(buffer))>0){
                //输出缓冲区的内容到浏览器，实现文件下载
                out.write(buffer, 0, len);
            }
            //关闭文件输入流
            in.close();
            //关闭输出流
            out.close();
        } catch (IOException e) {
            result.setErrMsg("系统异常，请稍后重试");
            print(result);
        }
    }
}
