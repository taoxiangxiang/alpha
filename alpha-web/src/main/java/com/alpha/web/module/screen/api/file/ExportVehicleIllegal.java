package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleIllegalDO;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.VehicleIllegalManager;
import com.alpha.query.VehicleIllegalQuery;
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
public class ExportVehicleIllegal extends BaseAjaxModule {

    @Resource
    private ExportExcelManager exportExcelManager;
    @Resource
    private VehicleIllegalManager vehicleIllegalManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("vehicleNO") String vehicleNO, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 1000;
            VehicleIllegalQuery vehicleIllegalQuery = new VehicleIllegalQuery();
            vehicleIllegalQuery.setPage(page);
            vehicleIllegalQuery.setPageSize(pageSize);
            vehicleIllegalQuery.setVehicleNO(vehicleNO);
            vehicleIllegalQuery.setStartDate(startDate == null ? null : CalendarUtil.formatDate(new Date(startDate), CalendarUtil.TIME_PATTERN));
            vehicleIllegalQuery.setEndDate(endDate == null ? null : CalendarUtil.formatDate(new Date(endDate), CalendarUtil.TIME_PATTERN));
            List<VehicleIllegalDO> list = vehicleIllegalManager.query(vehicleIllegalQuery);
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "违章记录_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
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
        titleNameList.add("违章日期");
        titleNameList.add("违章原因");
        titleNameList.add("罚款");
        titleNameList.add("扣分");
        titleNameList.add("违章地点");
        titleNameList.add("司机");
        titleNameList.add("备注");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("vehicleNO");
        fieldNameList.add("team");
        fieldNameList.add("illegalDate");
        fieldNameList.add("reason");
        fieldNameList.add("money");
        fieldNameList.add("point");
        fieldNameList.add("illegalAddress");
        fieldNameList.add("driverName");
        fieldNameList.add("remark");
        return fieldNameList;
    }
}
