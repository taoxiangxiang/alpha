package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.query.VehicleApplicationQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by taoxiang on 2017/5/21.
 */
public class ExportVehicleApplication extends BaseAjaxModule {

    @Resource
    private VehicleApplicationManager vehicleApplicationManager;
    @Resource
    private ExportExcelManager exportExcelManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("status") String status, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 1000;
            VehicleApplicationQuery vehicleApplicationQuery = new VehicleApplicationQuery();
            vehicleApplicationQuery.setPage(page);
            vehicleApplicationQuery.setPageSize(pageSize);
            vehicleApplicationQuery.setStartDate(startDate == null ? null : new Date(startDate));
            vehicleApplicationQuery.setEndDate(endDate == null ? null : new Date(endDate));
            if (status != null) {
                List<String> statusList = new ArrayList<String>();
                statusList.add(status);
                vehicleApplicationQuery.setStatusList(statusList);
            }
            List<VehicleApplicationDO> list = vehicleApplicationManager.query(vehicleApplicationQuery);
            dealId(list);
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "车辆申请单_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
        } catch (Exception e) {
            Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
            logger.error("ExportVehicleApplication execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }

    private void dealId(List<VehicleApplicationDO> vehicleApplicationDOList) {
        if (vehicleApplicationDOList == null || vehicleApplicationDOList.size() == 0) {
            return;
        }
        for (VehicleApplicationDO vehicleApplicationDO : vehicleApplicationDOList) {
            vehicleApplicationDO.setStatus(SystemConstant.vehicleApplicationStatusMap.get(vehicleApplicationDO.getStatus()));
        }
    }

    private List<String> initTitleNameList() {
        List<String> titleNameList = new ArrayList<String>();
        titleNameList.add("申请单号");
        titleNameList.add("申请单类型");
        titleNameList.add("车辆类型");
        titleNameList.add("用车原因");
        titleNameList.add("用车时间");
        titleNameList.add("预计回车时间");
        titleNameList.add("申请人");
        titleNameList.add("申请人部门");
        titleNameList.add("申请人电话");
        titleNameList.add("乘车人数");
        titleNameList.add("出发地点");
        titleNameList.add("目的地点");
        titleNameList.add("目的地点类型");
        titleNameList.add("联系人");
        titleNameList.add("联系电话");
        titleNameList.add("申请单状态");
        titleNameList.add("申请时间");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("applicationNO");
        fieldNameList.add("applicationType");
        fieldNameList.add("vehicleType");
        fieldNameList.add("applicationReason");
        fieldNameList.add("useDate");
        fieldNameList.add("predictBackDate");
        fieldNameList.add("applicant");
        fieldNameList.add("applicationDepartment");
        fieldNameList.add("applicantPhone");
        fieldNameList.add("personNumber");
        fieldNameList.add("startPlace");
        fieldNameList.add("endPlace");
        fieldNameList.add("endPlaceType");
        fieldNameList.add("contacts");
        fieldNameList.add("contactsPhone");
        fieldNameList.add("status");
        fieldNameList.add("gmtCreate");
        return fieldNameList;
    }
}
