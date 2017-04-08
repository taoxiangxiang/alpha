package com.alpha.web.module.screen.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.DriverDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.DriverQuery;
import com.alpha.query.SystemAccountQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.common.BaseModule;
import com.alpha.web.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/3/21.
 */
public class User extends BaseAjaxModule{

    private static final Logger logger = LoggerFactory.getLogger(User.class);
    @Resource
    private SystemAccountManager systemAccountManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("name") String name, @Param("id") Long id, Context context) {
        try {
            SystemAccountQuery systemAccountQuery = new SystemAccountQuery();
            if (id == null) {
                Result<List<SystemAccountDO>> result = new Result<List<SystemAccountDO>>();
                systemAccountQuery.setPage(page);
                systemAccountQuery.setPageSize(pageSize);
                systemAccountQuery.setName(name);
                List<SystemAccountDO> list = systemAccountManager.query(systemAccountQuery);
                result.setData(list);
                print(result);
            } else {
                Result<SystemAccountDO> result = new Result<SystemAccountDO>();
                systemAccountQuery.setId(id);
                List<SystemAccountDO> list = systemAccountManager.query(systemAccountQuery);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关数据");
                } else {
                    result.setData(list.get(0));
                }
                print(result);
            }
        } catch (Exception e) {
            Result<SystemAccountDO> result = new Result<SystemAccountDO>();
            logger.error("User execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }
}
