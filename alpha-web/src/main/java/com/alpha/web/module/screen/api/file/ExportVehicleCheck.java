package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.InsuranceDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleCheckDO;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.InsuranceManager;
import com.alpha.manager.VehicleCheckManager;
import com.alpha.query.InsuranceQuery;
import com.alpha.query.VehicleCheckQuery;
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
public class ExportVehicleCheck extends BaseAjaxModule {

    @Resource
    private ExportExcelManager exportExcelManager;
    @Resource
    private VehicleCheckManager vehicleCheckManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("vehicleNO") String vehicleNO, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 200;
            VehicleCheckQuery vehicleCheckQuery = new VehicleCheckQuery();
            PageResult<List<VehicleCheckDO>> result = new PageResult<List<VehicleCheckDO>>();
            vehicleCheckQuery.setPage(page);
            vehicleCheckQuery.setPageSize(pageSize);
            vehicleCheckQuery.setVehicleNO(vehicleNO);
            vehicleCheckQuery.setStartDate(startDate == null ? null : CalendarUtil.formatDate(new Date(startDate), CalendarUtil.TIME_PATTERN));
            vehicleCheckQuery.setEndDate(endDate == null ? null : CalendarUtil.formatDate(new Date(endDate), CalendarUtil.TIME_PATTERN));
            List<VehicleCheckDO> list = vehicleCheckManager.query(vehicleCheckQuery);
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "年检记录_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
        } catch (Exception e) {
            Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
            logger.error("ExportInsurance execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
            print(result);
        }
    }

    private List<String> initTitleNameList() {
        List<String> titleNameList = new ArrayList<String>();
        titleNameList.add("车牌号");
        titleNameList.add("所属车队");
        titleNameList.add("年检日期");
        titleNameList.add("年检号");
        titleNameList.add("年检费用");
        titleNameList.add("车管所");
        titleNameList.add("到期时间");
        titleNameList.add("经手人");
        titleNameList.add("备注");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("vehicleNO");
        fieldNameList.add("team");
        fieldNameList.add("checkDate");
        fieldNameList.add("checkNO");
        fieldNameList.add("money");
        fieldNameList.add("checkAddress");
        fieldNameList.add("endDate");
        fieldNameList.add("operator");
        fieldNameList.add("remark");
        return fieldNameList;
    }
}
