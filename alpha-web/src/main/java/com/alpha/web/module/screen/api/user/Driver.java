package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.DriverDO;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.DriverManager;
import com.alpha.query.DriverQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;
import sun.jvm.hotspot.debugger.Page;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class Driver extends BaseAjaxModule {

    @Resource
    private DriverManager driverManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("name") String name, @Param("team") String team,
                        @Param("id") Integer id, Context context) {
        try {
            DriverQuery driverQuery = new DriverQuery();
            if (id == null) {
                PageResult<List<DriverDO>> result = new PageResult<List<DriverDO>>();
                driverQuery.setPage(page);
                driverQuery.setPageSize(pageSize);
                driverQuery.setName(name);
                driverQuery.setTeam(team);
                List<DriverDO> list = driverManager.query(driverQuery);
                int number = driverManager.count(driverQuery);
                result.setPage(page);
                result.setPageSize(pageSize);
                result.setNumber(number);
                result.setData(list);
                print(result);
            } else {
                Result<DriverDO> result = new Result<DriverDO>();
                driverQuery.setId(id);
                List<DriverDO> list = driverManager.query(driverQuery);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关数据");
                } else {
                    result.setData(list.get(0));
                }
                print(result);
            }

        } catch (Exception e) {
            Result<VehicleDO> result = new Result<VehicleDO>();
            logger.error("Driver execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }
}