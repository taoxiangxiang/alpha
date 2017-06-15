package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.MaintainDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.MaintainManager;
import com.alpha.query.MaintainQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by taoxiang on 2017/5/21.
 */
public class ExportMaintain extends BaseAjaxModule {

    @Resource
    private ExportExcelManager exportExcelManager;
    @Resource
    private MaintainManager maintainManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("status") String status, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 1000;
            MaintainQuery maintainQuery = new MaintainQuery();
            maintainQuery.setPage(page);
            maintainQuery.setPageSize(pageSize);
            maintainQuery.setStartDate(startDate == null ? null : new Date(startDate));
            maintainQuery.setEndDate(endDate == null ? null : new Date(endDate));
            if (status != null) {
                List<String> statusList = new ArrayList<String>();
                statusList.add(status);
                maintainQuery.setStatusList(statusList);
            }
            List<MaintainDO> list = maintainManager.query(maintainQuery);
            dealId(list);
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "维保申请单_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
        } catch (Exception e) {
            Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
            logger.error("ExportMaintain execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }
    private void dealId(List<MaintainDO> maintainDOList) {
        if (maintainDOList == null || maintainDOList.size() == 0) {
            return;
        }
        for (MaintainDO maintainDO : maintainDOList) {
            maintainDO.setStatus(SystemConstant.maintainStatusMap.get(maintainDO.getStatus()));
        }
    }

    private List<String> initTitleNameList() {
        List<String> titleNameList = new ArrayList<String>();
        titleNameList.add("申请单号");
        titleNameList.add("车牌号");
        titleNameList.add("所属车队");
        titleNameList.add("申请人");
        titleNameList.add("申请人电话");
        titleNameList.add("预计取车时间");
        titleNameList.add("实际取车时间");
        titleNameList.add("维保类别");
        titleNameList.add("维保内容");
        titleNameList.add("维保厂地址");
        titleNameList.add("维保时间");
        titleNameList.add("车辆里程");
        titleNameList.add("申请原因");
        titleNameList.add("申请单状态");
        titleNameList.add("申请时间");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("applicationNO");
        fieldNameList.add("vehicleNO");
        fieldNameList.add("team");
        fieldNameList.add("applicant");
        fieldNameList.add("applicantPhone");
        fieldNameList.add("predictPickUpDate");
        fieldNameList.add("actualPickUpDate");
        fieldNameList.add("type");
        fieldNameList.add("maintainContent");
        fieldNameList.add("maintainAddress");
        fieldNameList.add("maintainDate");
        fieldNameList.add("mile");
        fieldNameList.add("reason");
        fieldNameList.add("status");
        fieldNameList.add("gmtCreate");
        return fieldNameList;
    }
}
