package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.SystemConstant;
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
            if (systemAccountDO == null) {
                print(new Result<String>("请先登录系统"));
                return;
            }
            VerifyRecordDO verifyRecordDO = new VerifyRecordDO();
            verifyRecordDO.setVerifyId(systemAccountDO.getId());
            verifyRecordDO.setVerifyName(systemAccountDO.getName());
            verifyRecordDO.setApplicationEvent(applicationEvent);
            verifyRecordDO.setApplicationId(applicationId);
            verifyRecordDO.setResult(result);
            verifyRecordDO.setRemark(remark);
            String checkParamRes = checkParam(verifyRecordDO);
            if (!"ok".equals(checkParamRes)) {
                result1.setErrMsg(checkParamRes);
                print(result1);
                return;
            }
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

    private String checkParam(VerifyRecordDO verifyRecordDO) {
        if (StringUtil.isBlank(verifyRecordDO.getApplicationEvent()) || verifyRecordDO.getApplicationId() <= 0) {
            return "无法识别该审核事件";
        }
        if (StringUtil.isBlank(verifyRecordDO.getResult()) || !SystemConstant.verifyResultMap.containsValue(verifyRecordDO.getResult())) {
            return "请填写审核结果";
        }
        if (StringUtil.isBlank(verifyRecordDO.getRemark())) {
            return "请填写备注";
        }
        return "ok";
    }
}
