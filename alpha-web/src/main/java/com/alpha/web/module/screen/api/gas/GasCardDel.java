package com.alpha.web.module.screen.api.gas;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.GasCardDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.GasCardManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class GasCardDel extends BaseAjaxModule {

    @Resource
    private GasCardManager gasCardManager;

    public void execute(@Param("id") int id, Context context) {
        Result<String> result = new Result<String>();
        try {
            SystemAccountDO curAccountDO = this.getAccount();
            if (curAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!curAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            GasCardDO gasCardDO = new GasCardDO();
            gasCardDO.setId(id);
            gasCardDO.setStatus(SystemConstant.GAS_CARD_DELETE);
            boolean res = gasCardManager.update(gasCardDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("GasCardDel execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
        }
        print(result);
    }
}
