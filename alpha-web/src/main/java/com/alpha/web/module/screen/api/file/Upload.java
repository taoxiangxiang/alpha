package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.web.common.BaseAjaxModule;
import java.io.*;
import com.alpha.web.domain.Result;
import org.apache.commons.fileupload.FileItem;

import java.util.Date;
import java.util.UUID;

/**
 * Created by taoxiang on 2017/4/23.
 */
public class Upload extends BaseAjaxModule {

    public void execute(@Param("file") FileItem fileItem, Context context) {
        Result<String> result = new Result<String>();
        try {
            if (fileItem.getSize() > 5 * 1024 * 1024) {
                result.setErrMsg("文件大小不能超过5M");
                print(result);
                return;
            }
            if(fileItem.getName() == null || fileItem.getName().trim().equals("")){
                result.setErrMsg("文件名不正确");
                print(result);
                return;
            }
            String res = uploadFile(fileItem);
            if (!res.equals("fail")) {
                result.setData(res);
            } else {
                result.setErrMsg(res);
            }
        } catch (Exception e) {
            logger.error("Upload execute catch exception, fileName=" + fileItem.getName(), e);
            result.setErrMsg("系统异常，请重试");
        }
        print(result);
    }

    private String uploadFile(FileItem item) {
        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = session.getServletContext().getRealPath("/") + "file/";
        try{
            File saveFile = new File(savePath);
            if (!saveFile.exists()) {
                //创建临时目录
                if (!saveFile.mkdir()) {
                    return "fail";
                }
            }
            //得到上传的文件名称，
            String filename = item.getName();
            if(filename == null || filename.trim().equals("")){
                return "fail";
            }
            //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
            //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
            filename = filename.substring(filename.lastIndexOf("\\")+1);
            //得到上传文件的扩展名
            String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
            //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
            System.out.println("上传的文件的扩展名是："+fileExtName);
            //获取item中的上传文件的输入流
            InputStream in = item.getInputStream();
            //得到文件保存的名称
            String saveFilename = makeFileName(filename);
            //创建一个文件输出流
            FileOutputStream out = new FileOutputStream(savePath + saveFilename);
            //创建一个缓冲区
            byte buffer[] = new byte[1024];
            //判断输入流中的数据是否已经读完的标识
            int len = 0;
            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
            while((len=in.read(buffer))>0){
                //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                out.write(buffer, 0, len);
            }
            //关闭输入流
            in.close();
            //关闭输出流
            out.close();
            return savePath + saveFilename;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    private String makeFileName(String filename){  //2.jpg
        //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        return CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION) + "_" + filename;
    }
}
