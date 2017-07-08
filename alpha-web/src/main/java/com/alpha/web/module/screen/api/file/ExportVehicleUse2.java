package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleUseDO;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.VehicleApplicationManager;
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
public class ExportVehicleUse2 extends BaseAjaxModule {

    @Resource
    private VehicleUseManager vehicleUseManager;
    @Resource
    private VehicleApplicationManager vehicleApplicationManager;
    @Resource
    private ExportExcelManager exportExcelManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("applicationId") String applicationId, @Param("vehicleNO") String vehicleNO,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("alreadyCheck") Boolean alreadyCheck,
                        @Param("team") String team, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 1000;
            SystemAccountDO systemAccountDO = getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请先登录系统"));
                return;
            }
            VehicleUseQuery vehicleUseQuery = new VehicleUseQuery();
            vehicleUseQuery.setPage(page);
            vehicleUseQuery.setPageSize(pageSize);
            vehicleUseQuery.setApplicationId(applicationId == null ? null :
                    (applicationId.startsWith("YC") ? Integer.valueOf(applicationId.substring(6)) : Integer.valueOf(applicationId)));
            /*权限判断*/
            if (systemAccountDO.isDriver()) {
                vehicleUseQuery.setDriverId(systemAccountDO.getDriverId());
            }
            if (systemAccountDO.hasNOAuth()) {
                vehicleUseQuery.setApplicationIdList(vehicleApplicationManager.queryByApplicantId(systemAccountDO.getId()));
            }
            vehicleUseQuery.setStartDate(startDate == null ? null : CalendarUtil.toString(new Date(startDate), CalendarUtil.TIME_PATTERN));
            vehicleUseQuery.setEndDate(endDate == null ? null : CalendarUtil.toString(new Date(endDate), CalendarUtil.TIME_PATTERN));
            vehicleUseQuery.setTeam(team);
            vehicleUseQuery.setVehicleNO(vehicleNO);
            vehicleUseQuery.setAlreadyCheck(alreadyCheck);
            List<VehicleUseDO> list = vehicleUseManager.query(vehicleUseQuery);
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "出车明细_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
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
        titleNameList.add("车牌号");
        titleNameList.add("出发地点");
        titleNameList.add("目的地点");
        titleNameList.add("申请人");
        titleNameList.add("用车人");
        titleNameList.add("是否登记");
        titleNameList.add("评价");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("vehicleApplicationDO.applicationNO");
        fieldNameList.add("vehicleApplicationDO.gmtCreate");
        fieldNameList.add("driverName");
        fieldNameList.add("vehicleNO");
        fieldNameList.add("vehicleApplicationDO.startPlace");
        fieldNameList.add("vehicleApplicationDO.endPlace");
        fieldNameList.add("vehicleApplicationDO.applicant");
        fieldNameList.add("vehicleApplicationDO.usePerson");
        fieldNameList.add("alreadyCheck");
        fieldNameList.add("point");
        return fieldNameList;
    }
}
