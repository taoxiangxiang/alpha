package com.alpha.web.module.screen.api.user;

import com.alpha.constans.YunUtil;
import com.alpha.domain.SystemAccountDO;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by taoxiang on 2017/5/7.
 */
public class SendCodeMsg extends BaseAjaxModule {

    private static final Logger logger = LoggerFactory.getLogger(ResetPassword.class);

    public void execute() {
        Result<String> result = new Result<String>();
        try {
            SystemAccountDO accountNow = this.getAccount();
            boolean res = YunUtil.sendCodeMsg(accountNow.getMobilePhone());
            if (res) {
                result.setData("发送成功");
            } else {
                result.setErrMsg("发送失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("SendCode execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }
}
