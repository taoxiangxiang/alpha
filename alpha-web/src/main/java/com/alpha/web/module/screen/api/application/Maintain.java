package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.MaintainDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.manager.MaintainManager;
import com.alpha.query.MaintainQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
public class Maintain extends BaseAjaxModule {

    @Resource
    private MaintainManager maintainManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                        @Param("status") String status, @Param("id") Integer id, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10;
            MaintainQuery maintainQuery = new MaintainQuery();
            if (id == null) {
                maintainQuery.setPage(page);
                maintainQuery.setPageSize(pageSize);
                maintainQuery.setStartDate(startDate);
                maintainQuery.setEndDate(endDate);
                if (status != null) {
                    List<String> statusList = new ArrayList<String>();
                    statusList.add(status);
                    maintainQuery.setStatusList(statusList);
                }
                List<MaintainDO> list = maintainManager.query(maintainQuery);
                int number = maintainManager.count(maintainQuery);
                PageResult<List<MaintainDO>> result = new PageResult<List<MaintainDO>>();
                result.setData(list);
                result.setPage(page);
                result.setPageSize(pageSize);
                result.setNumber(number);
                print(result);
            } else {
                Result<MaintainDO> result = new Result<MaintainDO>();
                maintainQuery.setId(id);
                List<MaintainDO> list = maintainManager.query(maintainQuery);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关申请单数据：id=" + id);
                } else {
                    result.setData(list.get(0));
                }
                print(result);
            }
        } catch (Exception e) {
            Result<MaintainDO> result = new Result<MaintainDO>();
            logger.error("Maintain execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }
}
