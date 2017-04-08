package com.alpha.web.module.screen.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.SystemAccountQuery;
import com.alpha.web.common.BaseModule;
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
public class Login extends BaseModule{

    private static final Logger logger = LoggerFactory.getLogger(Login.class);

    @Resource
    private SystemAccountManager systemAccountManager;

    public void execute(Context context) {
        try {
            SystemAccountQuery systemAccountQuery = new SystemAccountQuery();
            systemAccountQuery.setName("admin");
            List<SystemAccountDO> accountDOList = systemAccountManager.query(systemAccountQuery);
            if (accountDOList != null && accountDOList.size() >= 1) {
                String nick = accountDOList.get(0).getNick();
                session.setAttribute(SystemConstant.SESSION_NICK, nick);
                Cookie[] cookies = request.getCookies();
                setCookie(response, SystemConstant.COOKIE_NICK, nick);
            }
        } catch (Exception e) {
            logger.error("Login execute catch exception", e);
        }
    }

    public void setCookie(HttpServletResponse response, String name, String value) {
        // new一个Cookie对象,键值对为参数
        Cookie cookie = new Cookie(name, value);
        // tomcat下多应用共享
        cookie.setPath("/");
        // 如果cookie的值中含有中文时，需要对cookie进行编码，不然会产生乱码
        try {
            URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        cookie.setMaxAge(time);
        // 将Cookie添加到Response中,使之生效
        response.addCookie(cookie); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
        return;
    }
}
