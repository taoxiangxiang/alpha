package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleUseDO;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.VehicleUseManager;
import com.alpha.query.VehicleUseQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by taoxiang on 2017/5/21.
 */
public class ExportVehicleUse extends BaseAjaxModule {

    @Resource
    private VehicleUseManager vehicleUseManager;
    @Resource
    private ExportExcelManager exportExcelManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("applicationId") Integer applicationId,
                        @Param("vehicleNO") String vehicleNO, @Param("team") String team,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("status") String status, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 200;
            VehicleUseQuery vehicleUseQuery = new VehicleUseQuery();
            vehicleUseQuery.setPage(page);
            vehicleUseQuery.setPageSize(pageSize);
            vehicleUseQuery.setApplicationId(applicationId);
            vehicleUseQuery.setTeam(team);
            vehicleUseQuery.setVehicleNO(vehicleNO);
            vehicleUseQuery.setStartDate(startDate == null ? null : CalendarUtil.toString(new Date(startDate), CalendarUtil.TIME_PATTERN));
            vehicleUseQuery.setEndDate(endDate == null ? null : CalendarUtil.toString(new Date(endDate), CalendarUtil.TIME_PATTERN));
            List<VehicleUseDO> list = vehicleUseManager.query(vehicleUseQuery);
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "出车明细单_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
        } catch (Exception e) {
            Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
            logger.error("ExportVehicleApplication execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }

    private List<String> initTitleNameList() {
        List<String> titleNameList = new ArrayList<String>();
        titleNameList.add("申请单号");
        titleNameList.add("用车日期");
        titleNameList.add("司机姓名");
        titleNameList.add("身份证号");
        titleNameList.add("所属车队");
        titleNameList.add("手机号码");
        titleNameList.add("车牌号");
        titleNameList.add("实际开始时间");
        titleNameList.add("实际结束时间");
        titleNameList.add("实际回车时间");
        titleNameList.add("出车里程");
        titleNameList.add("结束里程");
        titleNameList.add("服务费用");
        titleNameList.add("差旅费");
        titleNameList.add("加班费");
        titleNameList.add("过路费用");
        titleNameList.add("过桥费用");
        titleNameList.add("洗车费用");
        titleNameList.add("停车费用");
        titleNameList.add("其他费用");
        titleNameList.add("备注");
        titleNameList.add("评价");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("applicationId");
        fieldNameList.add("vehicleApplicationDO.gmtCreate");
        fieldNameList.add("driverName");
        fieldNameList.add("driverDO.citizenId");
        fieldNameList.add("driverDO.team");
        fieldNameList.add("driverDO.mobilePhone");
        fieldNameList.add("vehicleNO");
        fieldNameList.add("actualStartDate");
        fieldNameList.add("actualEndDate");
        fieldNameList.add("actualBackDate");
        fieldNameList.add("startMile");
        fieldNameList.add("endMile");
        fieldNameList.add("fuwuFee");
        fieldNameList.add("cailvFee");
        fieldNameList.add("jiabanFee");
        fieldNameList.add("guoluFee");
        fieldNameList.add("guoqiaoFee");
        fieldNameList.add("xicheFee");
        fieldNameList.add("tingcheFee");
        fieldNameList.add("otherFee");
        fieldNameList.add("remark");
        fieldNameList.add("point");
        return fieldNameList;
    }
}
