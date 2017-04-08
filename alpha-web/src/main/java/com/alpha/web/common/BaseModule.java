package com.alpha.web.common;

import com.alpha.constans.SystemConstant;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.SystemAccountQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/3.
 */
public class BaseModule {

    public static Logger logger = LoggerFactory.getLogger(BaseModule.class);

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected HttpSession session;
    @Resource
    private SystemAccountManager systemAccountManager;

    protected void print(String s) {
        try {
            response.getWriter().print(s);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    protected void redirect(String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    protected String getSessionNick() {
        String nick = (String)session.getAttribute(SystemConstant.SESSION_NICK);
//        if (nick != null) {
//            return UnicodeUtil.decodeUnicode(nick);
//        }
        return nick;
    }

    protected SystemAccountDO getAccount() {
        try {
            SystemAccountQuery systemAccountQuery = new SystemAccountQuery();
            systemAccountQuery.setName(getSessionNick());
            List<SystemAccountDO> accountDOList = systemAccountManager.query(systemAccountQuery);
            if (accountDOList == null || accountDOList.size() == 0) {
                return null;
            }
            return accountDOList.get(0);
        } catch (Exception e) {
            logger.error("BaseModule getAccount catch exception", e);
            return null;
        }
    }

}
