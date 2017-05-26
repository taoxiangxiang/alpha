package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.dao.TeamDao;
import com.alpha.domain.TeamDO;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/27.
 */
public class TeamAdd extends BaseAjaxModule {

    @Resource
    private TeamDao teamDao;

    public void execute(@Param("team") String team, Context context) {
        Result<String> result = new Result<String>();
        try {
            TeamDO teamDO = new TeamDO();
            teamDO.setStatus(SystemConstant.TEAM_ON_LINE);
            teamDO.setTeam(team);
            boolean res = teamDao.insert(teamDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("TeamAdd execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
        }
        print(result);
    }
}
