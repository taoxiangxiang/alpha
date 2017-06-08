package com.alpha.web.module.screen.api.department;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.PartnerDepartmentDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.PartnerDepartmentManager;
import com.alpha.query.PartnerDepartmentQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoxiang on 2017/5/9.
 */
public class PartnerDepartment extends BaseAjaxModule {

    @Resource
    private PartnerDepartmentManager partnerDepartmentManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("departmentName") String departmentName, @Param("id") Integer id,
                        @Param("type") String type,
                        Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10;
            SystemAccountDO curAccountDO = this.getAccount();
            if (curAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            PartnerDepartmentQuery partnerDepartmentQuery = new PartnerDepartmentQuery();
            if (id == null) {
                PageResult<List<PartnerDepartmentDO>> result = new PageResult<List<PartnerDepartmentDO>>();
                partnerDepartmentQuery.setPage(page);
                partnerDepartmentQuery.setPageSize(pageSize);
                partnerDepartmentQuery.setDepartmentName(departmentName);
                List<String> statusList = new ArrayList<String>();
                statusList.add(SystemConstant.DEPARTMENT_ON_LINE);
                partnerDepartmentQuery.setStatusList(statusList);
                partnerDepartmentQuery.setType(type);
                List<PartnerDepartmentDO> list = partnerDepartmentManager.query(partnerDepartmentQuery);
                if (list != null) {
                    for (PartnerDepartmentDO partnerDepartmentDO : list) {
                        partnerDepartmentDO.setStatus(SystemConstant.departmentStatusMap.get(partnerDepartmentDO.getStatus()));
                    }
                }
                int number = partnerDepartmentManager.count(partnerDepartmentQuery);
                result.setData(list);
                result.setPage(page);
                result.setPageSize(pageSize);
                result.setNumber(number);
                print(result);
            } else {
                Result<PartnerDepartmentDO> result = new Result<PartnerDepartmentDO>();
                partnerDepartmentQuery.setId(id);
                List<PartnerDepartmentDO> list = partnerDepartmentManager.query(partnerDepartmentQuery);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关数据");
                } else {
                    PartnerDepartmentDO partnerDepartmentDO = list.get(0);
                    partnerDepartmentDO.setStatus(SystemConstant.departmentStatusMap.get(partnerDepartmentDO.getStatus()));
                    result.setData(partnerDepartmentDO);
                }
                print(result);
            }

        } catch (Exception e) {
            Result<PartnerDepartmentDO> result = new Result<PartnerDepartmentDO>();
            logger.error("Vehicle execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }
}
