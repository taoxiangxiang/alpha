package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.dao.MsgCodeDao;
import com.alpha.domain.MsgCodeDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/5/7.
 */
public class ModifyPassword extends BaseAjaxModule {

    private static final Logger logger = LoggerFactory.getLogger(ModifyPassword.class);

    @Resource
    private SystemAccountManager systemAccountManager;
    @Resource
    private MsgCodeDao msgCodeDao;

    public void execute(@Param("nick") String nick, @Param("code") String code, @Param("newPassword1") String newPassword1,
                        @Param("newPassword2") String newPassword2, Context context) {
        Result<String> result = new Result<String>();
        try {
            SystemAccountDO accountNow = this.getAccount();
            if (accountNow == null) {
                if (StringUtil.isBlank(nick)) {
                    result.setErrMsg("请输入当前用户昵称");
                    print(result);
                    return;
                }
                accountNow = systemAccountManager.queryByNick(nick);
            }
            if (accountNow == null) {
                result.setErrMsg("当前账户不存在");
                print(result);
                return;
            }
            SystemAccountDO systemAccountDO = new SystemAccountDO();
            systemAccountDO.setId(accountNow.getId());
            if (StringUtil.isBlank(newPassword1) || !newPassword1.equals(newPassword2)) {
                result.setErrMsg("密码不能为空，且两次密码必须一致");
                print(result);
                return;
            }
            systemAccountDO.setPassword(encoderByMd5(newPassword1));
            MsgCodeDO msgCodeDO = msgCodeDao.queryRecentOneByPhone(accountNow.getMobilePhone());
            if (msgCodeDO == null) {
                result.setErrMsg("您的验证码已经失效");
                print(result);
                return;
            }

            if (!msgCodeDO.getCode().equals(code)) {
                result.setErrMsg("您的验证码输入错误");
                print(result);
                return;
            }
            boolean res = systemAccountManager.update(systemAccountDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
            print(result);
        } catch (Exception e) {
            logger.error("ModifyPassword execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }
}
