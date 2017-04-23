package com.alpha.web.common;

import com.alpha.constans.SystemConstant;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.SystemAccountQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    protected String getSessionName() {
        try {
            String name = (String)session.getAttribute(SystemConstant.SESSION_NAME);
            if (name != null) {
                return URLDecoder.decode(name, "utf-8");
            }
        } catch (Exception e) {
            logger.error("BaseModule getSessionName catch exception", e);
        }
        return null;
    }

    protected SystemAccountDO getAccount() {
        try {
            SystemAccountQuery systemAccountQuery = new SystemAccountQuery();
            String sessionName = getSessionName();
            if (sessionName == null) {
                return null;
            }
            systemAccountQuery.setName(getSessionName());
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

    protected String encoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        return base64en.encode(md5.digest(str.getBytes("utf-8")));
    }

}
