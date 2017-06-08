package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleApplicationSumDO;
import com.alpha.domain.VehicleUseSumDO;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.manager.VehicleUseManager;
import com.alpha.query.VehicleApplicationQuery;
import com.alpha.query.VehicleUseQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/5/21.
 */
public class ExportDepartmentVehicleStatistics extends BaseAjaxModule {

    @Resource
    private VehicleApplicationManager vehicleApplicationManager;
    @Resource
    private ExportExcelManager exportExcelManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("departmentName") String departmentName,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 200;
            VehicleApplicationQuery vehicleApplicationQuery = new VehicleApplicationQuery();
            vehicleApplicationQuery.setPage(page);
            vehicleApplicationQuery.setPageSize(pageSize);
            vehicleApplicationQuery.setStartDate(startDate == null ? null : new Date(startDate));
            vehicleApplicationQuery.setEndDate(endDate == null ? null : new Date(endDate));
            vehicleApplicationQuery.setApplicationDepartment(departmentName);
            List<String> statusList = new ArrayList<String>();
            statusList.add(SystemConstant.VEHICLE_VERIFY_PASS);
            vehicleApplicationQuery.setStatusList(statusList);
            List<VehicleApplicationSumDO> list = vehicleApplicationManager.queryGroupByDepartment(vehicleApplicationQuery);
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "部门用车统计_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
        } catch (Exception e) {
            Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
            logger.error("ExportDepartmentVehicleStatistics execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }

    private List<String> initTitleNameList() {
        List<String> titleNameList = new ArrayList<String>();
        titleNameList.add("部门名称");
        titleNameList.add("使用次数");
        titleNameList.add("所属部门");
        titleNameList.add("部门联系人");
        titleNameList.add("部门联系人电话");
        titleNameList.add("部门地址");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("departmentName");
        fieldNameList.add("number");
        fieldNameList.add("belongDepartmentName");
        fieldNameList.add("departmentContact");
        fieldNameList.add("departmentContactPhone");
        fieldNameList.add("departmentAddress");
        return fieldNameList;
    }
}
