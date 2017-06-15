package com.alpha.timejob;

import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverDO;
import com.alpha.domain.LeaveDO;
import com.alpha.manager.DBManager;
import com.alpha.manager.DriverManager;
import com.alpha.manager.LeaveManager;
import com.alpha.query.LeaveQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by taoxiang on 2017/5/23.
 */
@Component("backUpJob")
public class BackUpJob implements InitializingBean {

    private static final long LOCAL_CACHE_TIMER_START = 10 * 1000;
    private static final long LOCAL_CACHE_TIMER_PERIOD = 24 * 3600 * 1000;

    private static final Logger logger = LoggerFactory.getLogger(BackUpJob.class);

    @Resource
    private DBManager dbManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        Timer statTimer = new Timer();
        statTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                dealBackUpJob();
            }
        }, LOCAL_CACHE_TIMER_START, LOCAL_CACHE_TIMER_PERIOD);
    }

    private void dealBackUpJob() {
        try{
            String savePath = this.getClass().getResource("/").getPath();
            savePath = savePath.replace("alpha/WEB-INF/classes", "backup") + "backup_" + CalendarUtil.toString(new Date(), CalendarUtil.DATE_FMT_0 )+ ".sql";
            dbManager.dbBackup(savePath);
        } catch (Exception e) {
            logger.error("BackUpJob dealLeaveJob catch exception", e);
        }
    }
}
