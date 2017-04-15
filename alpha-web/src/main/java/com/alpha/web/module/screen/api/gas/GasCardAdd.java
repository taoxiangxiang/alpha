package com.alpha.web.module.screen.api.gas;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.GasCardDO;
import com.alpha.manager.GasCardManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class GasCardAdd extends BaseAjaxModule {

    @Resource
    private GasCardManager gasCardManager;

    public void execute(@Param("gasCardNO") String gasCardNO,
                        @Param("gasCardType") String gasCardType,
                        @Param("amount") Double amount,
                        Context context) {
        Result<String> result = new Result<String>();
        try {
            GasCardDO gasCardDO = new GasCardDO();
            gasCardDO.setGasCardNO(gasCardNO);
            gasCardDO.setGasCardType(gasCardType);
            gasCardDO.setAmount(amount);
            gasCardDO.setStatus(SystemConstant.GAS_CARD_ON_LINE);
            boolean res = gasCardManager.insert(gasCardDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("GasCardAdd execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
        }
        print(result);
    }
}