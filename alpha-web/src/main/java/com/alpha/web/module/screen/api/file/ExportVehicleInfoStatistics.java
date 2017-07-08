package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.*;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.VehicleGasManager;
import com.alpha.manager.VehicleManager;
import com.alpha.query.VehicleGasQuery;
import com.alpha.query.VehicleQuery;
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
public class ExportVehicleInfoStatistics extends BaseAjaxModule {

    @Resource
    private VehicleManager vehicleManager;
    @Resource
    private ExportExcelManager exportExcelManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("vehicleNO") String vehicleNO, @Param("team") String team) {
        try {
            SystemAccountDO curAccountDO = this.getAccount();
            if (curAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!curAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 1000;
            VehicleQuery vehicleQuery = new VehicleQuery();
            vehicleQuery.setPage(page);
            vehicleQuery.setPageSize(pageSize);
            vehicleQuery.setVehicleNO(vehicleNO);
            vehicleQuery.setTeam(team);
            List<String> statusList = new ArrayList<String>();
            statusList.add(SystemConstant.VEHICLE_CAN_USE);
            statusList.add(SystemConstant.VEHICLE_OFF_LINE);
            statusList.add(SystemConstant.VEHICLE_USING);
            statusList.add(SystemConstant.VEHICLE_MAINTAIN);
            vehicleQuery.setStatusList(statusList);
            List<VehicleDO> vehicleDOList = vehicleManager.query(vehicleQuery);
            List<VehicleInfoDO> list = vehicleManager.getVehicleInfo(vehicleDOList, vehicleNO, team, startDate, endDate);
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "车辆信息统计_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
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
        titleNameList.add("总油耗金额");
        titleNameList.add("总油量");
        titleNameList.add("维修次数");
        titleNameList.add("维修费用（总）");
        titleNameList.add("保养次数");
        titleNameList.add("保养费用（总）");
        titleNameList.add("违章总次数");
        titleNameList.add("违章总罚款");
        titleNameList.add("违章总扣分");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("vehicleNO");
        fieldNameList.add("team");
        fieldNameList.add("gasMoney");
        fieldNameList.add("gasAmount");
        fieldNameList.add("weiXiuNumber");
        fieldNameList.add("weiXiuMoney");
        fieldNameList.add("baoYangNumber");
        fieldNameList.add("baoYangMoney");
        fieldNameList.add("fineNumber");
        fieldNameList.add("fineMoney");
        fieldNameList.add("finePoint");
        return fieldNameList;
    }
}
