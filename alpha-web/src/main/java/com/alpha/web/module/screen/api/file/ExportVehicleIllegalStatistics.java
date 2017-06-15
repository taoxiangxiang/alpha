package com.alpha.web.module.screen.api.file;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.MaintainSumDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleIllegalSumDO;
import com.alpha.manager.ExportExcelManager;
import com.alpha.manager.MaintainManager;
import com.alpha.manager.VehicleIllegalManager;
import com.alpha.query.MaintainQuery;
import com.alpha.query.VehicleIllegalQuery;
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
public class ExportVehicleIllegalStatistics extends BaseAjaxModule {

    @Resource
    private VehicleIllegalManager vehicleIllegalManager;
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
            VehicleIllegalQuery vehicleIllegalQuery = new VehicleIllegalQuery();
            PageResult<List<VehicleIllegalSumDO>> result = new PageResult<List<VehicleIllegalSumDO>>();
            vehicleIllegalQuery.setPage(page);
            vehicleIllegalQuery.setTeam(team);
            vehicleIllegalQuery.setPageSize(pageSize);
            vehicleIllegalQuery.setVehicleNO(vehicleNO);
            vehicleIllegalQuery.setStartDate(startDate == null ? null : CalendarUtil.formatDate(new Date(startDate), CalendarUtil.TIME_PATTERN));
            vehicleIllegalQuery.setEndDate(endDate == null ? null : CalendarUtil.formatDate(new Date(endDate), CalendarUtil.TIME_PATTERN));
            List<VehicleIllegalSumDO> list = vehicleIllegalManager.queryGroupByVehicle(vehicleIllegalQuery);
            XSSFWorkbook xssfWorkbook = exportExcelManager.exportExcel(initTitleNameList(), initFieldNameList(), list);
            printExcel(xssfWorkbook, "车辆违章统计_" + CalendarUtil.toString(new Date(), CalendarUtil.TIME_PATTERN_SESSION));
        } catch (Exception e) {
            Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
            logger.error("ExportVehicleIllegalStatistics execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }

    private List<String> initTitleNameList() {
        List<String> titleNameList = new ArrayList<String>();
        titleNameList.add("车牌号");
        titleNameList.add("所属车队");
        titleNameList.add("违章次数");
        titleNameList.add("罚款");
        titleNameList.add("扣分");
        return titleNameList;
    }

    private List<String> initFieldNameList() {
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("vehicleNO");
        fieldNameList.add("team");
        fieldNameList.add("number");
        fieldNameList.add("money");
        fieldNameList.add("point");
        return fieldNameList;
    }
}
