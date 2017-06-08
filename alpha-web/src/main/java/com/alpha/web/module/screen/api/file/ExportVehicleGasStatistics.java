package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleApplicationSumDO;
import com.alpha.domain.VehicleGasSumDO;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.manager.VehicleGasManager;
import com.alpha.query.VehicleApplicationQuery;
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
public class ExportVehicleGasStatistics extends BaseAjaxModule {

    @Resource
    private VehicleGasManager vehicleGasManager;
    @Resource
    private ExportExcelManager exportExcelManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("vehicleNO") String vehicleNO, @Param("team") String team,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 200;
            VehicleGasQuery vehicleGasQuery = new VehicleGasQuery();
            vehicleGasQuery.setPage(page);
            vehicleGasQuery.setPageSize(pageSize);
            vehicleGasQuery.setStartDate(startDate == null ? null : CalendarUtil.formatDate(new Date(startDate), CalendarUtil.TIME_PATTERN));
            vehicleGasQuery.setEndDate(endDate == null ? null : CalendarUtil.formatDate(new Date(endDate), CalendarUtil.TIME_PATTERN));
            vehicleGasQuery.setVehicleNO(vehicleNO);
            vehicleGasQuery.setTeam(team);
            List<VehicleGasSumDO> list = vehicleGasManager.queryGroupByVehicle(vehicleGasQuery);
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "车辆油耗统计_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
        } catch (Exception e) {
            Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
            logger.error("ExportVehicleGasStatistics execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }

    private List<String> initTitleNameList() {
        List<String> titleNameList = new ArrayList<String>();
        titleNameList.add("车牌号");
        titleNameList.add("车队");
        titleNameList.add("油卡编号");
        titleNameList.add("金额");
        titleNameList.add("加油量");
        titleNameList.add("加油里程");
        titleNameList.add("平均油耗");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("vehicleNO");
        fieldNameList.add("team");
        fieldNameList.add("gasCardNO");
        fieldNameList.add("money");
        fieldNameList.add("amount");
        fieldNameList.add("mile");
        fieldNameList.add("cost");
        return fieldNameList;
    }
}
