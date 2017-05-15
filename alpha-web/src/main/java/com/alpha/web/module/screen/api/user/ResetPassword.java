package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.SystemAccountQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/5/7.
 */
public class ResetPassword extends BaseAjaxModule {

    private static final Logger logger = LoggerFactory.getLogger(ResetPassword.class);

    @Resource
    private SystemAccountManager systemAccountManager;

    public void execute(@Param("id") Integer id, Context context) {
        Result<String> result = new Result<String>();
        try {
            SystemAccountDO accountNow = this.getAccount();
            SystemAccountQuery systemAccountQuery = new SystemAccountQuery();
            systemAccountQuery.setId(id);
            List<SystemAccountDO> systemAccountDOList = systemAccountManager.query(systemAccountQuery);
            if (systemAccountDOList == null || systemAccountDOList.size() == 0) {
                result.setErrMsg("你操作重置密码的用户不存在");
                print(result);
                return;
            }
            SystemAccountDO systemAccountDO = systemAccountDOList.get(0);
            systemAccountDO.setId(id);
            systemAccountDO.setPassword(encoderByMd5(systemAccountDO.getCitizenId().substring(12)));
            boolean res = systemAccountManager.update(systemAccountDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("ModifyPassword execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }
}
