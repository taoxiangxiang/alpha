package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.LeaveSumDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleGasSumDO;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.LeaveManager;
import com.alpha.manager.VehicleGasManager;
import com.alpha.query.LeaveQuery;
import com.alpha.query.VehicleGasQuery;
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
public class ExportLeaveStatistics extends BaseAjaxModule {

    @Resource
    private LeaveManager leaveManager;
    @Resource
    private ExportExcelManager exportExcelManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("driverName") String driverName, @Param("team") String team,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10000;
            LeaveQuery leaveQuery = new LeaveQuery();
            PageResult<List<LeaveSumDO>> result = new PageResult<List<LeaveSumDO>>();
            leaveQuery.setPage(page);
            leaveQuery.setPageSize(pageSize);
            leaveQuery.setDriverName(driverName);
            leaveQuery.setTeam(team);
            List<String> statusList = new ArrayList<String>();
            statusList.add(SystemConstant.LEAVE_VERIFY_PASS);
            leaveQuery.setStatusList(statusList);
            List<LeaveSumDO> list = leaveManager.getSumList(leaveManager.query(leaveQuery), startDate == null ? null : new Date(startDate), endDate == null ? null : new Date(endDate));
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "病事假统计_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
        } catch (Exception e) {
            Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
            logger.error("ExportVehicleGasStatistics execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }

    private List<String> initTitleNameList() {
        List<String> titleNameList = new ArrayList<String>();
        titleNameList.add("司机姓名");
        titleNameList.add("电话号码");
        titleNameList.add("车队");
        titleNameList.add("性别");
        titleNameList.add("身份证号");
        titleNameList.add("请假天数");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("driverName");
        fieldNameList.add("mobilePhone");
        fieldNameList.add("team");
        fieldNameList.add("sex");
        fieldNameList.add("citizenId");
        fieldNameList.add("number");
        return fieldNameList;
    }
}
