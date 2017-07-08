package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.YunUtil;
import com.alpha.dao.MsgCodeDao;
import com.alpha.domain.MsgCodeDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * Created by taoxiang on 2017/5/7.
 */
public class SendCodeMsg extends BaseAjaxModule {

    private static final Logger logger = LoggerFactory.getLogger(ResetPassword.class);

    @Resource
    private MsgCodeDao msgCodeDao;
    @Resource
    private SystemAccountManager systemAccountManager;

    public void execute(@Param("nick") String nick) {
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
            String code =  YunUtil.getRandNum(6);
            //频率控制 同一个手机号，一分钟最多一条，一个小时内最多7条
            long oneMinutesAgo = new Date().getTime() - 60L * 1000L;
            long oneHourAgo = new Date().getTime() - 60L * 60L * 1000L;
            List<MsgCodeDO> list1 = msgCodeDao.queryByPhone(accountNow.getMobilePhone(), new Date(oneMinutesAgo));
            if (list1 != null && list1.size() > 0) {
                result.setErrMsg("1分钟内最多发送一次验证码");
                print(result);
                return;
            }
            List<MsgCodeDO> list2 = msgCodeDao.queryByPhone(accountNow.getMobilePhone(), new Date(oneHourAgo));
            if (list2 != null && list2.size() > 6) {
                result.setErrMsg("1小时内最多发送7次验证码");
                print(result);
                return;
            }
            boolean res = YunUtil.sendCodeMsg(accountNow.getMobilePhone(), code);
            MsgCodeDO msgCodeDO = new MsgCodeDO();
            msgCodeDO.setPhoneNumber(accountNow.getMobilePhone());
            msgCodeDO.setCode(code);
            if (res && msgCodeDao.insert(msgCodeDO)) {
                result.setData("发送成功");
            } else {
                result.setErrMsg("发送失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("SendCode execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
        }
        print(result);
    }
}
