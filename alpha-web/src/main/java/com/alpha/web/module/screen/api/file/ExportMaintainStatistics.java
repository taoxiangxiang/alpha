package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.LeaveSumDO;
import com.alpha.domain.MaintainSumDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.LeaveManager;
import com.alpha.manager.MaintainManager;
import com.alpha.query.LeaveQuery;
import com.alpha.query.MaintainQuery;
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
public class ExportMaintainStatistics extends BaseAjaxModule {

    @Resource
    private MaintainManager maintainManager;
    @Resource
    private ExportExcelManager exportExcelManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("vehicleNO") String vehicleNO, @Param("team") String team) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10000;
            MaintainQuery maintainQuery = new MaintainQuery();
            maintainQuery.setPage(page);
            maintainQuery.setPageSize(pageSize);
            maintainQuery.setStartDate(startDate == null ? null : new Date(startDate));
            maintainQuery.setEndDate(endDate == null ? null : new Date(endDate));
            maintainQuery.setVehicleNO(vehicleNO);
            maintainQuery.setTeam(team);
            List<String> statusList = new ArrayList<String>();
            statusList.add(SystemConstant.MAINTAIN_VERIFY_PASS);
            maintainQuery.setStatusList(statusList);
            List<MaintainSumDO> list = maintainManager.queryGroupByVehicle(maintainQuery);
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "维修保养统计_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
        } catch (Exception e) {
            Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
            logger.error("ExportMaintainStatistics execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }

    private List<String> initTitleNameList() {
        List<String> titleNameList = new ArrayList<String>();
        titleNameList.add("车牌号");
        titleNameList.add("所属车队");
        titleNameList.add("维修次数");
        titleNameList.add("维修费用");
        titleNameList.add("保养次数");
        titleNameList.add("保养费用");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("vehicleNO");
        fieldNameList.add("team");
        fieldNameList.add("weiXiuNumber");
        fieldNameList.add("weiXiuMoney");
        fieldNameList.add("baoYangNumber");
        fieldNameList.add("baoYangMoney");
        return fieldNameList;
    }
}
