package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleUseDO;
import com.alpha.domain.VehicleUseSumDO;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.VehicleUseManager;
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
public class ExportVehicleUseStatistics extends BaseAjaxModule {

    @Resource
    private VehicleUseManager vehicleUseManager;
    @Resource
    private ExportExcelManager exportExcelManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("driverName") String driverName,@Param("team") String team,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 200;
            VehicleUseQuery vehicleUseQuery = new VehicleUseQuery();
            vehicleUseQuery.setPage(page);
            vehicleUseQuery.setPageSize(pageSize);
            vehicleUseQuery.setDriverName(driverName);
            vehicleUseQuery.setTeam(team);
            vehicleUseQuery.setStartDate(startDate == null ? null : CalendarUtil.toString(new Date(startDate), CalendarUtil.TIME_PATTERN));
            vehicleUseQuery.setEndDate(endDate == null ? null : CalendarUtil.toString(new Date(endDate), CalendarUtil.TIME_PATTERN));
            List<VehicleUseSumDO> list = vehicleUseManager.queryGroupByDriver(vehicleUseQuery);
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "司机出车统计_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
        } catch (Exception e) {
            Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
            logger.error("ExportVehicleUseStatistics execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }

    private List<String> initTitleNameList() {
        List<String> titleNameList = new ArrayList<String>();
        titleNameList.add("司机姓名");
        titleNameList.add("车队");
        titleNameList.add("出车次数");
        titleNameList.add("出车里程");
        titleNameList.add("总费用");
        titleNameList.add("服务费用");
        titleNameList.add("差旅费");
        titleNameList.add("加班费");
        titleNameList.add("过路费用");
        titleNameList.add("过桥费用");
        titleNameList.add("洗车费用");
        titleNameList.add("停车费用");
        titleNameList.add("其他费用");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("driverName");
        fieldNameList.add("team");
        fieldNameList.add("number");
        fieldNameList.add("mile");
        fieldNameList.add("allFee");
        fieldNameList.add("fuwuFee");
        fieldNameList.add("cailvFee");
        fieldNameList.add("jiabanFee");
        fieldNameList.add("guoluFee");
        fieldNameList.add("guoqiaoFee");
        fieldNameList.add("xicheFee");
        fieldNameList.add("tingcheFee");
        fieldNameList.add("otherFee");
        return fieldNameList;
    }
}
