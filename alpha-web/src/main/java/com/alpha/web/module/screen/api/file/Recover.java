package com.alpha.web.module.screen.api.file;

import com.alpha.manager.DBManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/5/14.
 */
public class Recover extends BaseAjaxModule {

    @Resource
    private DBManager dbManager;

    public void execute() {
        Result<String> result = new Result<String>();
        String savePath = session.getServletContext().getRealPath("/");
        savePath = savePath.replace("alpha", "backup") + "backup.sql";
        if (dbManager.dbRecover(savePath)) {
            result.setData("操作成功");
        } else {
            result.setData("操作失败，请稍后重试");
        }
    }
}
