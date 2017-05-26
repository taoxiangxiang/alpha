package com.alpha.manager.impl;

import com.alpha.constans.CalendarUtil;
import com.alpha.constans.YunUtil;
import com.alpha.dao.VehicleUseDao;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleUseDO;
import com.alpha.domain.VehicleUseSumDO;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.manager.VehicleUseManager;
import com.alpha.query.VehicleApplicationQuery;
import com.alpha.query.VehicleUseQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taoxiang on 2017/4/16.
 */
@Component("vehicleUseManager")
public class VehicleUseManagerImpl implements VehicleUseManager {

    @Resource
    private VehicleUseDao vehicleUseDao;
    @Resource
    private VehicleApplicationManager vehicleApplicationManager;

    @Override
    @Transactional(rollbackForClassName="Exception")
    public String batchInsert(List<VehicleUseDO> vehicleUseDOList) throws Exception {
        for (VehicleUseDO vehicleUseDO : vehicleUseDOList) {
            if (!insert(vehicleUseDO)) {
                throw new Exception("数据库写入失败");
            }
            YunUtil.sendVehicleVerifyPass(vehicleUseDO);
        }
        return "true";
    }

    @Override
    public boolean insert(VehicleUseDO vehicleUseDO) {
        return vehicleUseDao.insert(vehicleUseDO);
    }

    @Override
    public List<VehicleUseDO> query(VehicleUseQuery vehicleUseQuery) {
        List<VehicleUseDO> vehicleUseDOList = vehicleUseDao.query(vehicleUseQuery);
        addApplication(vehicleUseDOList);
        return vehicleUseDOList;
    }

    @Override
    public int count(VehicleUseQuery vehicleUseQuery) {
        return vehicleUseDao.count(vehicleUseQuery);
    }

    @Override
    public boolean update(VehicleUseDO vehicleUseDO) {
        return vehicleUseDao.update(vehicleUseDO);
    }

    @Override
    public List<VehicleUseSumDO> queryGroupByDriver(VehicleUseQuery vehicleUseQuery) {
        List<VehicleUseSumDO> list =  vehicleUseDao.queryGroupByDriver(vehicleUseQuery);
        if (list == null || list.size() == 0) return list;
        for (VehicleUseSumDO vehicleUseSumDO : list) {
            Double allFee = vehicleUseSumDO.getCailvFee() + vehicleUseSumDO.getFuwuFee()
                    + vehicleUseSumDO.getJiabanFee() + vehicleUseSumDO.getGuoluFee()
                    + vehicleUseSumDO.getGuoqiaoFee() + vehicleUseSumDO.getTingcheFee()
                    + vehicleUseSumDO.getXicheFee() + vehicleUseSumDO.getOtherFee();
            vehicleUseSumDO.setAllFee(allFee);
        }
        return list;
    }

    @Override
    public int countGroupByDriver(VehicleUseQuery vehicleUseQuery) {
        return vehicleUseDao.countGroupByDriver(vehicleUseQuery);
    }

    private void addApplication(List<VehicleUseDO> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        List<Integer> applicationIdList = new ArrayList<Integer>();
        for (VehicleUseDO vehicleUseDO : list) {
            applicationIdList.add(vehicleUseDO.getApplicationId());
        }
        VehicleApplicationQuery vehicleApplicationQuery = new VehicleApplicationQuery();
        vehicleApplicationQuery.setIdList(applicationIdList);
        List<VehicleApplicationDO> vehicleApplicationDOList = vehicleApplicationManager.query(vehicleApplicationQuery);
        Map<Integer, VehicleApplicationDO> map = new HashMap<Integer, VehicleApplicationDO>();
        if (vehicleApplicationDOList == null || vehicleApplicationDOList.size() == 0) {
            return;
        }
        for (VehicleApplicationDO vehicleApplicationDO : vehicleApplicationDOList) {

            map.put(vehicleApplicationDO.getId(), vehicleApplicationDO);
        }
        for (VehicleUseDO vehicleUseDO : list) {
            VehicleApplicationDO vehicleApplicationDO = map.get(vehicleUseDO.getApplicationId());
            vehicleUseDO.setVehicleApplicationDO(vehicleApplicationDO);
            vehicleUseDO.setApplicationNO(vehicleApplicationDO.getApplicationNO());
            vehicleUseDO.setAlreadyCheck(vehicleUseDO.getGmtModified().getTime() - vehicleUseDO.getGmtCreate().getTime() > 10000L);
        }
    }
}
