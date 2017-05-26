package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.LeaveDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleCheckDO;
import com.alpha.domain.VerifyRecordDO;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.LeaveManager;
import com.alpha.manager.VehicleCheckManager;
import com.alpha.query.LeaveQuery;
import com.alpha.query.VehicleCheckQuery;
import com.alpha.query.VerifyRecordQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by taoxiang on 2017/5/21.
 */
public class ExportLeave extends BaseAjaxModule {

    @Resource
    private ExportExcelManager exportExcelManager;
    @Resource
    private LeaveManager leaveManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("status") String status, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 200;
            LeaveQuery leaveQuery = new LeaveQuery();
            leaveQuery.setPage(page);
            leaveQuery.setPageSize(pageSize);
            leaveQuery.setStartDate(startDate == null ? null : new Date(startDate));
            leaveQuery.setEndDate(endDate == null ? null : new Date(endDate));
            if (status != null) {
                List<String> statusList = new ArrayList<String>();
                statusList.add(status);
                leaveQuery.setStatusList(statusList);
            }
            List<LeaveDO> list = leaveManager.query(leaveQuery);
            dealStatus(list);
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "病事假申请_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
        } catch (Exception e) {
            Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
            logger.error("ExportLeave execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
            print(result);
        }
    }

    private List<String> initTitleNameList() {
        List<String> titleNameList = new ArrayList<String>();
        titleNameList.add("申请单编号");
        titleNameList.add("司机姓名");
        titleNameList.add("电话号码");
        titleNameList.add("车队");
        titleNameList.add("开始时间");
        titleNameList.add("结束时间");
        titleNameList.add("原因");
        titleNameList.add("状态");
        titleNameList.add("备注");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("applicationNO");
        fieldNameList.add("driverName");
        fieldNameList.add("mobilePhone");
        fieldNameList.add("team");
        fieldNameList.add("startDate");
        fieldNameList.add("endDate");
        fieldNameList.add("reason");
        fieldNameList.add("status");
        fieldNameList.add("remark");
        return fieldNameList;
    }

    private void dealStatus(List<LeaveDO> leaveDOList) {
        if (leaveDOList == null || leaveDOList.size() == 0) {
            return;
        }
        for (LeaveDO leaveDO : leaveDOList) {
            leaveDO.setStatus(SystemConstant.leaveStatusMap.get(leaveDO.getStatus()));
        }
    }
}
