package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.YunUtil;
import com.alpha.domain.MaintainDO;
import com.alpha.domain.PartnerDepartmentDO;
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
                        @Param("actualStartDate") Long actualPickUpDate,
                        @Param("maintainContent") String maintainContent,
                        @Param("maintainDate") Long maintainDate,
                        @Param("mile") Integer mile,
                        @Param("remark") String remark2) {
        Result<String> result = new Result<String>();
        try {
            PartnerDepartmentQuery partnerDepartmentQuery = new PartnerDepartmentQuery();
            partnerDepartmentQuery.setId(maintainDepartmentId);
            PartnerDepartmentDO partnerDepartmentDO = null;
            if (maintainDepartmentId > 0) {
                List<PartnerDepartmentDO> partnerDepartmentDOList = partnerDepartmentManager.query(partnerDepartmentQuery);
                if (partnerDepartmentDOList == null || partnerDepartmentDOList.size() == 0) {
                    result.setErrMsg("指定的维修单位不存在");
                    print(result);
                    return;
                }
                partnerDepartmentDO = partnerDepartmentDOList.get(0);
            }
            MaintainDO maintainDO = new MaintainDO();
            maintainDO.setId(id);
            if (partnerDepartmentDO != null) {
                maintainDO.setMaintainAddress(partnerDepartmentDO.getAddress());
                maintainDO.setMaintainDepartmentName(partnerDepartmentDO.getDepartmentName());
                maintainDO.setMaintainPhone(partnerDepartmentDO.getMobilePhone());
            }
            maintainDO.setActualPickUpDate(actualPickUpDate == null ? null : CalendarUtil.toString(new Date(actualPickUpDate), CalendarUtil.TIME_PATTERN));
            maintainDO.setMaintainContent(maintainContent);
            maintainDO.setMaintainDate(maintainDate == null ? null :CalendarUtil.toString(new Date(maintainDate), CalendarUtil.TIME_PATTERN));
            maintainDO.setMile(mile);
            maintainDO.setPickUpRemark(remark2);
            boolean res = maintainManager.update(maintainDO);
            if (res) {
                if (partnerDepartmentDO != null) {
                    MaintainQuery maintainQuery = new MaintainQuery();
                    maintainQuery.setId(id);
                    MaintainDO maintainDOInDB = maintainManager.query(maintainQuery).get(0);
                    YunUtil.sendMaintainVerifyPass(maintainDOInDB);
                }
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
