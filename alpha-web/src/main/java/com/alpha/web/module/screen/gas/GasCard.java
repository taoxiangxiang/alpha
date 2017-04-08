package com.alpha.web.module.screen.gas;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.DepartmentDO;
import com.alpha.domain.GasCardDO;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.DepartmentManager;
import com.alpha.manager.GasCardManager;
import com.alpha.query.DepartmentQuery;
import com.alpha.query.GasCardQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class GasCard extends BaseAjaxModule {
    @Resource
    private GasCardManager gasCardManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("id") Integer id, Context context) {
        try {
            GasCardQuery gasCardQuery = new GasCardQuery();
            if (id == null) {
                Result<List<GasCardDO>> result = new Result<List<GasCardDO>>();
                gasCardQuery.setPage(page);
                gasCardQuery.setPageSize(pageSize);
                List<GasCardDO> list = gasCardManager.query(gasCardQuery);
                result.setData(list);
                print(result);
            } else {
                Result<GasCardDO> result = new Result<GasCardDO>();
                gasCardQuery.setId(id);
                List<GasCardDO> list = gasCardManager.query(gasCardQuery);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关数据");
                } else {
                    result.setData(list.get(0));
                }
                print(result);
            }

        } catch (Exception e) {
            Result<VehicleDO> result = new Result<VehicleDO>();
            logger.error("Vehicle execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }
}
