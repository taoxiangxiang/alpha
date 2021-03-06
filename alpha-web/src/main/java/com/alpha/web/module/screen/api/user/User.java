package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.SystemAccountQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoxiang on 2017/3/21.
 */
public class User extends BaseAjaxModule{

    private static final Logger logger = LoggerFactory.getLogger(User.class);
    @Resource
    private SystemAccountManager systemAccountManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("name") String name, @Param("id") Integer id, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 1000;
            SystemAccountDO curAccountDO = this.getAccount();
            if (curAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            SystemAccountQuery systemAccountQuery = new SystemAccountQuery();
            if (id == null) {
                PageResult<List<SystemAccountDO>> result = new PageResult<List<SystemAccountDO>>();
                systemAccountQuery.setPage(page);
                systemAccountQuery.setPageSize(pageSize);
                systemAccountQuery.setName(name);
                List<String> typeList = new ArrayList<String>();
                typeList.add(SystemConstant.USER_TYPE_NO_AUTH);
                typeList.add(SystemConstant.USER_TYPE_HAS_AUTH);
                systemAccountQuery.setTypeList(typeList);

                List<String> statusList = new ArrayList<String>();
                statusList.add(SystemConstant.ACCOUNT_ON_LINE);
                systemAccountQuery.setStatusList(statusList);
                List<SystemAccountDO> list = systemAccountManager.query(systemAccountQuery);
                if (list != null) {
                    for (SystemAccountDO systemAccountDO : list) {
                        systemAccountDO.setAuthType(transAuth(systemAccountDO.getAuthType()));
                    }
                }
                int number = systemAccountManager.count(systemAccountQuery);
                result.setPage(page);
                result.setPageSize(pageSize);
                result.setNumber(number);
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

    private String transAuth(String authTypeCode) {
        if (StringUtil.isBlank(authTypeCode)) {
            return authTypeCode;
        }
        String[] authArray = authTypeCode.split(",");
        StringBuilder sb = new StringBuilder();
        for (String auth : authArray) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(SystemConstant.authTypeMap.get(auth));
        }
        return sb.toString();
    }
}
