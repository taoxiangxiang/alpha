package com.alpha.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.SystemAccountQuery;
import com.alpha.web.common.BaseModule;
import com.alpha.web.module.screen.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class Default extends BaseModule {

    @Resource
    private SystemAccountManager systemAccountManager;

    public void execute(Context context) {
        try {
            String nick = this.getSessionNick();
            context.put("nick", nick);
        } catch (Exception e) {
            logger.error("User execute catch exception", e);
        }
    }
}
