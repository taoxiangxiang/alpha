package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.manager.DBManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import org.apache.commons.fileupload.FileItem;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by taoxiang on 2017/5/14.
 */
public class BackUp extends BaseAjaxModule{

    @Resource
    private DBManager dbManager;

    public void execute() {
        Result<String> result = new Result<String>();
        String savePath = session.getServletContext().getRealPath("/");
        savePath = savePath.replace("alpha", "backup") + "backup_" + CalendarUtil.toString(new Date(), CalendarUtil.DATE_FMT_0 )+ ".sql";
        if (dbManager.dbBackup(savePath)) {
            result.setData("操作成功");
        } else {
            result.setData("操作失败，请稍后重试");
        }
        print(result);
    }
}
