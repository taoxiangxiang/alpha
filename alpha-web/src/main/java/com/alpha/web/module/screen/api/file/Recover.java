package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.manager.DBManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by taoxiang on 2017/5/14.
 */
public class Recover extends BaseAjaxModule {

    @Resource
    private DBManager dbManager;

    public void execute(@Param("recoverDate") Long recoverDate) {
        Result<String> result = new Result<String>();
        if (recoverDate == null) {
            result.setErrMsg("请选择还原日期");
        }
        String savePath = session.getServletContext().getRealPath("/");
        savePath = savePath.replace("alpha", "backup") + "backup_" + CalendarUtil.toString(new Date(), CalendarUtil.DATE_FMT_0 )+ ".sql";
        String res = dbManager.dbRecover(savePath);
        if ("ok".equals(res)) {
            result.setData("操作成功");
        } else {
            result.setErrMsg(res);
        }
        print(result);
    }
}
