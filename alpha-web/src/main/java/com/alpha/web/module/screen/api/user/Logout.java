package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alpha.constans.SystemConstant;
import com.alpha.manager.SystemAccountManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by taoxiang on 2017/4/23.
 */
public class Logout extends BaseAjaxModule {

    private static final Logger logger = LoggerFactory.getLogger(Login.class);

    @Resource
    private SystemAccountManager systemAccountManager;

    public void execute(Context context) {
        Result<String> result = new Result<String>();
        try {
            if (session != null) {
                session.removeAttribute(SystemConstant.SESSION_NAME);
                removeCookie(response, SystemConstant.COOKIE_NAME);
            }
            result.setData("操作成功");
        } catch (Exception e) {
            result.setErrMsg("系统异常，请重试操作");
            logger.error("Login execute catch exception", e);
        }
        print(result);
    }

    private void removeCookie(HttpServletResponse response, String name) throws UnsupportedEncodingException {
        Cookie cookie = new Cookie(name, null);
        // tomcat下多应用共享
        cookie.setPath("/");
        cookie.setMaxAge(0);
        // 将Cookie添加到Response中,使之生效
        response.addCookie(cookie); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
    }
}
