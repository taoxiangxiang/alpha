package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.dao.TeamDao;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.TeamDO;
import com.alpha.query.TeamQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.List;

import java.util.Date;

/**
 * Created by taoxiang on 2017/5/26.
 */
public class Team extends BaseAjaxModule {

    @Resource
    private TeamDao teamDao;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("team") String team, Context context) {
        page = page > 0 ? page : 1;
        pageSize = pageSize > 0 ? pageSize : 10;
        SystemAccountDO systemAccountDO = this.getAccount();
        if (systemAccountDO == null) {
            print(new Result<String>("请登录系统"));
            return;
        }
        PageResult<List<TeamDO>> result = new PageResult<List<TeamDO>>();
        try {
            TeamQuery teamQuery = new TeamQuery();
            teamQuery.setTeam(team);
            teamQuery.setStatus(SystemConstant.TEAM_ON_LINE);
            List<TeamDO> list = teamDao.query(teamQuery);
            int number = teamDao.count(teamQuery);
            result.setData(list);
            result.setPage(page);
            result.setPageSize(pageSize);
            result.setNumber(number);
            result.setData(list);
            print(result);
        } catch (Exception e) {
            logger.error("VehicleCheckUpdate execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
            print(result);
        }
    }
}
