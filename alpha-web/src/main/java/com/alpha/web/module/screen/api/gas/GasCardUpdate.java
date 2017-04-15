package com.alpha.web.module.screen.api.gas;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.GasCardDO;
import com.alpha.manager.GasCardManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class GasCardUpdate extends BaseAjaxModule {

    @Resource
    private GasCardManager gasCardManager;

    public void execute(@Param("gasCardNO") String gasCardNO,
                        @Param("gasCardType") String gasCardType,
                        @Param("amount") Double amount,
                        @Param("id") int id,
                        Context context) {
        Result<String> result = new Result<String>();
        try {
            GasCardDO gasCardDO = new GasCardDO();
            gasCardDO.setId(id);
            gasCardDO.setGasCardNO(gasCardNO);
            gasCardDO.setGasCardType(gasCardType);
            gasCardDO.setAmount(amount);
            boolean res = gasCardManager.update(gasCardDO);
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
