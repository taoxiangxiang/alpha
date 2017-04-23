package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.SystemAccountQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/3.
 */
public class LoginUser extends BaseAjaxModule{

    private static final Logger logger = LoggerFactory.getLogger(LoginUser.class);

    @Resource
    private SystemAccountManager systemAccountManager;

    public void execute(Context context) {
        Result<SystemAccountDO> result = new Result<SystemAccountDO>();
        try {
            SystemAccountDO systemAccountDO = this.getAccount();
            if (systemAccountDO != null) {
                systemAccountDO.setPassword(null);
                result.setData(this.getAccount());
            } else {
                result.setErrMsg("当前登录用户信息过期，请重新登录");
            }
        } catch (Exception e) {
            result.setErrMsg("系统异常，请重试操作");
            logger.error("Login execute catch exception", e);
        }
        print(result);
    }
}
