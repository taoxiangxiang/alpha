package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.constans.YunUtil;
import com.alpha.domain.MaintainDO;
import com.alpha.domain.PartnerDepartmentDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.MaintainManager;
import com.alpha.manager.PartnerDepartmentManager;
import com.alpha.query.MaintainQuery;
import com.alpha.query.PartnerDepartmentQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.alpha.web.common.BaseModule.logger;

/**
 * Created by taoxiang on 2017/4/26.
 */
public class MaintainUpdate extends BaseAjaxModule {

    @Resource
    private MaintainManager maintainManager;
    @Resource
    private PartnerDepartmentManager partnerDepartmentManager;

    public void execute(@Param("id") int id,
                        @Param("maintainDepartmentId") int maintainDepartmentId,
                        @Param("actualPickUpDate") Long actualPickUpDate,
                        @Param("maintainContent") String maintainContent,
                        @Param("maintainDate") Long maintainDate,
                        @Param("mile") int mile, @Param("money") double money, @Param("file2") String file2,
                        @Param(name="pickUpRemark", defaultValue="") String pickUpRemark) {
        Result<String> result = new Result<String>();
        try {
            pickUpRemark = (pickUpRemark == null ? "" : pickUpRemark);
            SystemAccountDO systemAccountDO = this.getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            MaintainDO maintainDOInDB = maintainManager.queryById(id);
            if (maintainDOInDB == null) {
                result.setErrMsg("系统没有查询到对应的维保单");
                print(result);
                return;
            }
            if ((maintainDOInDB.getMaintainDepartmentId() == null || maintainDOInDB.getMaintainDepartmentId() <= 0) && maintainDepartmentId <= 0 ) {
                result.setErrMsg("请先指定维保单位");
                print(result);
                return;
            }

            PartnerDepartmentQuery partnerDepartmentQuery = new PartnerDepartmentQuery();
            partnerDepartmentQuery.setId(maintainDepartmentId);
            PartnerDepartmentDO partnerDepartmentDO = null;
            if (maintainDepartmentId > 0) {
                if (systemAccountDO.getAuthType() == null || !systemAccountDO.getAuthType().contains(SystemConstant.AUTH_TYPE_MAINTAIN_SCHEDULE)) {
                    result.setErrMsg("您没有维保派出权限");
                    print(result);
                    return;
                }
                List<PartnerDepartmentDO> partnerDepartmentDOList = partnerDepartmentManager.query(partnerDepartmentQuery);
                if (partnerDepartmentDOList == null || partnerDepartmentDOList.size() == 0) {
                    result.setErrMsg("指定的维修单位不存在");
                    print(result);
                    return;
                }
                partnerDepartmentDO = partnerDepartmentDOList.get(0);
            } else {
                if (!systemAccountDO.hasAuth() && maintainDOInDB.getApplicantId() != systemAccountDO.getId()) {
                    result.setErrMsg("这不是您的维保单");
                    print(result);
                    return;
                }
                if (actualPickUpDate == null) {
                    result.setErrMsg("请填写实际取车时间");
                    print(result);
                    return;
                }
                if (StringUtil.isBlank(maintainContent)) {
                    result.setErrMsg("请填写维保内容");
                    print(result);
                    return;
                }
                if (StringUtil.isBlank(maintainContent)) {
                    result.setErrMsg("请填写维保时间");
                    print(result);
                    return;
                }
                if (mile <= 0) {
                    result.setErrMsg("请填写车辆里程");
                    print(result);
                    return;
                }
                if (money <= 0) {
                    result.setErrMsg("请填写费用");
                    print(result);
                    return;
                }
            }
            MaintainDO maintainDO = new MaintainDO();
            maintainDO.setId(id);
            if (partnerDepartmentDO != null) {
                maintainDO.setMaintainDepartmentId(partnerDepartmentDO.getId());
                maintainDO.setMaintainAddress(partnerDepartmentDO.getAddress());
                maintainDO.setMaintainDepartmentName(partnerDepartmentDO.getDepartmentName());
                maintainDO.setMaintainPhone(partnerDepartmentDO.getMobilePhone());
            } else {
                maintainDO.setActualPickUpDate(actualPickUpDate == null ? null : new Date(actualPickUpDate));
                maintainDO.setMaintainContent(maintainContent);
                maintainDO.setMaintainDate(maintainDate == null ? null : new Date(maintainDate));
                maintainDO.setMile(mile);
                maintainDO.setMoney(money);
                maintainDO.setPickUpRemark(pickUpRemark);
                maintainDO.setFile2(file2);
            }
            boolean res = false;
            if (partnerDepartmentDO != null) {
                if (SystemConstant.MAINTAIN_VERIFY_PASS.equals(maintainDOInDB.getStatus())) {
                    maintainDO.setStatus(SystemConstant.MAINTAIN_ALREADY_SCHEDULE);
                }
                res = maintainManager.sendMaintain(maintainDO);
            } else {
                if (SystemConstant.MAINTAIN_ALREADY_SCHEDULE.equals(maintainDOInDB.getStatus())) {
                    maintainDO.setStatus(SystemConstant.MAINTAIN_ALREADY_PICK_UP);
                }
                res = maintainManager.pickUpMaintain(maintainDO);
            }
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("MaintainUpdate execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
        }
        print(result);
    }
}
