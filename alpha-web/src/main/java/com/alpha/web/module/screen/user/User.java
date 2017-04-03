package com.alpha.web.module.screen.user;

import com.alibaba.citrus.turbine.Context;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.SystemAccountQuery;
import com.alpha.web.common.BaseModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/3/21.
 */
public class User extends BaseModule{

    private static final Logger logger = LoggerFactory.getLogger(User.class);
    @Resource
    private SystemAccountManager systemAccountManager;

    public void execute(Context context) {
        try {
            String nick = this.getSessionNick();
            context.put("nick", nick);
            SystemAccountQuery systemAccountQuery = new SystemAccountQuery();
            context.put("list", systemAccountManager.query(systemAccountQuery));
        } catch (Exception e) {
            logger.error("User execute catch exception", e);
        }
    }
}
