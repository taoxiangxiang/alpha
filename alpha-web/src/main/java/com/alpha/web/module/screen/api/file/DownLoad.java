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
        FileInputStream in = null;
        try {
            String savePath = session.getServletContext().getRealPath("/");
            savePath = savePath.replace("alpha", "file");
            in = new FileInputStream(savePath + fileUrl);
        } catch (FileNotFoundException e) {
            result.setErrMsg("文件不存在");
            print(result);
            return;
        }

        try {
            downFile(fileUrl, in);
        } catch (IOException e) {
            result.setErrMsg("系统异常，请稍后重试");
            print(result);
        }
    }
}
