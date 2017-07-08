package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.SystemAccountQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.common.BaseModule;
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
public class Login extends BaseAjaxModule{

    private static final Logger logger = LoggerFactory.getLogger(Login.class);

    @Resource
    private SystemAccountManager systemAccountManager;

    public void execute(@Param("name") String nick, @Param("password") String password, Context context) {
        Result<String> result = new Result<String>();
        try {
            SystemAccountQuery systemAccountQuery = new SystemAccountQuery();
            systemAccountQuery.setNick(nick);
            List<SystemAccountDO> accountDOList = systemAccountManager.query(systemAccountQuery);
            if (accountDOList != null && accountDOList.size() >= 1) {
                String passwordInDB = accountDOList.get(0).getPassword();
                if (password != null && encoderByMd5(password).equals(passwordInDB)) {
                    session.setAttribute(SystemConstant.SESSION_NAME, nick);
                    setCookie(response, SystemConstant.COOKIE_NAME, nick);
                    result.setData("登录成功");
                    print(result);
                    return;
                }
                result.setErrMsg("密码与账户不匹配");
            } else {
                result.setErrMsg("账户不存在，请联系管理员");
            }
        } catch (Exception e) {
            result.setErrMsg("系统异常，请重试操作");
            logger.error("Login execute catch exception", e);
        }
        print(result);
    }


}
