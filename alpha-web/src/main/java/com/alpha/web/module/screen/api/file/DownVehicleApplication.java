package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.query.VehicleApplicationQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import freemarker.template.TemplateException;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taoxiang on 2017/5/16.
 */
public class DownVehicleApplication extends BaseAjaxModule {

    @Resource
    private VehicleApplicationManager vehicleApplicationManager;

    public void execute(@Param("id") int id, Context context) {
        Result<String> result = new Result<String>();
        List<VehicleApplicationDO> vehicleApplicationDOList = null;
        try {
            VehicleApplicationQuery vehicleApplicationQuery = new VehicleApplicationQuery();
            vehicleApplicationQuery.setId(id);
            vehicleApplicationDOList = vehicleApplicationManager.query(vehicleApplicationQuery);
        } catch (Exception e) {
            result.setErrMsg("系统异常，请稍后重试");
            print(result);
            return;
        }
        if (vehicleApplicationDOList == null || vehicleApplicationDOList.size() == 0) {
            result.setErrMsg("申请单不存在");
            print(result);
            return;
        }
        Map<String,Object> dataMap = initDataMap(vehicleApplicationDOList.get(0));
        String templateWord = "vehicleApplication.xml";
        try {
            downWord(dataMap, templateWord);
        } catch (IOException e) {
            result.setErrMsg("IO异常，请稍后重试");
        } catch (TemplateException e) {
            result.setErrMsg("申请单模版不存在，请稍后重试");
        }
        print(result);
    }

    private Map<String,Object> initDataMap(VehicleApplicationDO vehicleApplicationDO) {
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("id", vehicleApplicationDO.getId());
        dataMap.put("gmtCreate", CalendarUtil.toString(vehicleApplicationDO.getGmtCreate(), CalendarUtil.TIME_PATTERN));
        dataMap.put("applicationDepartment", vehicleApplicationDO.getApplicationDepartment());
        dataMap.put("applicationType", vehicleApplicationDO.getApplicationType());
        dataMap.put("applicant", vehicleApplicationDO.getApplicant());
        dataMap.put("applicantPhone", vehicleApplicationDO.getApplicantPhone());
        dataMap.put("startPlace", vehicleApplicationDO.getStartPlace());
        dataMap.put("endPlace", vehicleApplicationDO.getEndPlace());
        dataMap.put("vehicleType", vehicleApplicationDO.getVehicleType());
        dataMap.put("personNumber", vehicleApplicationDO.getPersonNumber());
        dataMap.put("useDate", vehicleApplicationDO.getUseDate());
        dataMap.put("predictBackDate", vehicleApplicationDO.getPredictBackDate());
        dataMap.put("applicationType", vehicleApplicationDO.getApplicationType());
        dataMap.put("applicationReason", vehicleApplicationDO.getApplicationReason());
        return dataMap;
    }
}
