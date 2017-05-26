package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleGasDO;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.VehicleGasManager;
import com.alpha.query.VehicleGasQuery;
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
public class ExportVehicleGas extends BaseAjaxModule {

    @Resource
    private ExportExcelManager exportExcelManager;
    @Resource
    private VehicleGasManager vehicleGasManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("vehicleNO") String vehicleNO, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 200;
            VehicleGasQuery vehicleGasQuery = new VehicleGasQuery();
            vehicleGasQuery.setPage(page);
            vehicleGasQuery.setPageSize(pageSize);
            vehicleGasQuery.setVehicleNO(vehicleNO);
            vehicleGasQuery.setStartDate(startDate == null ? null : CalendarUtil.formatDate(new Date(startDate), CalendarUtil.TIME_PATTERN));
            vehicleGasQuery.setEndDate(endDate == null ? null : CalendarUtil.formatDate(new Date(endDate), CalendarUtil.TIME_PATTERN));
            List<VehicleGasDO> list = vehicleGasManager.query(vehicleGasQuery);
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "加油记录_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
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
        titleNameList.add("加油日期");
        titleNameList.add("油气站");
        titleNameList.add("付款方式");
        titleNameList.add("油料标号");
        titleNameList.add("油卡编号");
        titleNameList.add("单价");
        titleNameList.add("金额");
        titleNameList.add("加油量");
        titleNameList.add("本次加油里程");
        titleNameList.add("上次加油里程");

        titleNameList.add("经手人");
        titleNameList.add("备注");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("vehicleNO");
        fieldNameList.add("team");
        fieldNameList.add("gasDate");
        fieldNameList.add("gasAddress");
        fieldNameList.add("payType");
        fieldNameList.add("gasType");
        fieldNameList.add("gasCardNO");
        fieldNameList.add("price");
        fieldNameList.add("money");
        fieldNameList.add("amount");
        fieldNameList.add("curMile");
        fieldNameList.add("beforeMile");
        fieldNameList.add("operator");
        fieldNameList.add("remark");
        return fieldNameList;
    }
}
