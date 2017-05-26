package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.VehicleAccidentDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleIllegalDO;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.VehicleAccidentManager;
import com.alpha.manager.VehicleIllegalManager;
import com.alpha.query.VehicleAccidentQuery;
import com.alpha.query.VehicleIllegalQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/5/21.
 */
public class ExportVehicleAccident extends BaseAjaxModule {

    @Resource
    private ExportExcelManager exportExcelManager;
    @Resource
    private VehicleAccidentManager vehicleAccidentManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("vehicleNO") String vehicleNO, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 200;
            VehicleAccidentQuery vehicleAccidentQuery = new VehicleAccidentQuery();
            vehicleAccidentQuery.setPage(page);
            vehicleAccidentQuery.setPageSize(pageSize);
            vehicleAccidentQuery.setVehicleNO(vehicleNO);
            vehicleAccidentQuery.setStartDate(startDate == null ? null : CalendarUtil.formatDate(new Date(startDate), CalendarUtil.TIME_PATTERN));
            vehicleAccidentQuery.setEndDate(endDate == null ? null : CalendarUtil.formatDate(new Date(endDate), CalendarUtil.TIME_PATTERN));
            List<VehicleAccidentDO> list = vehicleAccidentManager.query(vehicleAccidentQuery);
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "事故记录_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
        } catch (Exception e) {
            Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
            logger.error("ExportVehicleIllegal execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
            print(result);
        }
    }

    private List<String> initTitleNameList() {
        List<String> titleNameList = new ArrayList<String>();
        titleNameList.add("车牌号");
        titleNameList.add("所属车队");
        titleNameList.add("事故日期");
        titleNameList.add("司机");
        titleNameList.add("事故地点");
        titleNameList.add("事故说明");
        titleNameList.add("处理结果");
        titleNameList.add("处理情况");
        titleNameList.add("定损人");
        titleNameList.add("维修地点");
        titleNameList.add("责任认定");
        titleNameList.add("保险赔偿金额");
        titleNameList.add("备注");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("vehicleNO");
        fieldNameList.add("team");
        fieldNameList.add("accidentDate");
        fieldNameList.add("driverName");
        fieldNameList.add("accidentAddress");
        fieldNameList.add("accidentDesc");
        fieldNameList.add("result");
        fieldNameList.add("dealDesc");
        fieldNameList.add("liablePerson");
        fieldNameList.add("maintainAddress");
        fieldNameList.add("liableDesc");
        fieldNameList.add("money");
        fieldNameList.add("remark");
        return fieldNameList;
    }
}
