package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VerifyRecordDO;
import com.alpha.manager.VerifyRecordManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/6.
 */
public class VerifyEventAdd extends BaseAjaxModule {

    @Resource
    private VerifyRecordManager verifyRecordManager;

    public void execute(@Param("applicationEvent") String applicationEvent,
                        @Param("applicationId") int applicationId,
                        @Param("result") String result,
                        @Param("remark") String remark, Context context) {
        Result<String> result1 = new Result<String>();
        try {
            SystemAccountDO systemAccountDO = this.getAccount();
            VerifyRecordDO verifyRecordDO = new VerifyRecordDO();
            verifyRecordDO.setVerifyId(systemAccountDO.getId());
            verifyRecordDO.setVerifyName(systemAccountDO.getName());
            verifyRecordDO.setApplicationEvent(applicationEvent);
            verifyRecordDO.setApplicationId(applicationId);
            verifyRecordDO.setResult(result);
            verifyRecordDO.setRemark(remark);
            String res = verifyRecordManager.verify(verifyRecordDO);
            if (res.equals("true")) {
                result1.setData("操作成功");
            } else {
                result1.setErrMsg(res);
            }
        } catch (Exception e) {
            logger.error("VerifyEventAdd execute catch exception", e);
            result1.setErrMsg("系统异常，请重试");
        }
        print(result1);
    }
}
