package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.MaintainDO;
import com.alpha.manager.MaintainManager;
import com.alpha.query.MaintainQuery;
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

    public void execute(@Param("id") int id,
                        @Param("maintainAddress") String maintainAddress,
                        @Param("actualStartDate") Date actualPickUpDate,
                        @Param("maintainContent") String maintainContent,
                        @Param("maintainDate") Date maintainDate,
                        @Param("mile") Integer mile,
                        @Param("remark2") String remark2) {
        Result<String> result = new Result<String>();
        try {
            MaintainDO maintainDO = new MaintainDO();
            maintainDO.setId(id);
            maintainDO.setMaintainAddress(maintainAddress);
            maintainDO.setActualPickUpDate(CalendarUtil.toString(actualPickUpDate, CalendarUtil.TIME_PATTERN));
            maintainDO.setMaintainContent(maintainContent);
            maintainDO.setMaintainDate(CalendarUtil.toString(maintainDate, CalendarUtil.TIME_PATTERN));
            maintainDO.setMile(mile);
            maintainDO.setRemark2(remark2);
            boolean res = maintainManager.update(maintainDO);
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
