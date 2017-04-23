package com.alpha.manager.impl;

import com.alpha.dao.VehicleUseDao;
import com.alpha.domain.VehicleUseDO;
import com.alpha.manager.VehicleUseManager;
import com.alpha.query.VehicleUseQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/16.
 */
@Component("vehicleUseManager")
public class VehicleUseManagerImpl implements VehicleUseManager {

    @Resource
    private VehicleUseDao vehicleUseDao;

    @Override
    @Transactional(rollbackForClassName="Exception")
    public String batchInsert(List<VehicleUseDO> vehicleUseDOList) throws Exception {
        for (VehicleUseDO vehicleUseDO : vehicleUseDOList) {
            if (!insert(vehicleUseDO)) {
                throw new Exception("数据库写入失败");
            }
        }
        return "true";
    }

    @Override
    public boolean insert(VehicleUseDO vehicleUseDO) {
        return vehicleUseDao.insert(vehicleUseDO);
    }

    @Override
    public List<VehicleUseDO> query(VehicleUseQuery vehicleUseQuery) {
        return vehicleUseDao.query(vehicleUseQuery);
    }

    @Override
    public int count(VehicleUseQuery vehicleUseQuery) {
        return vehicleUseDao.count(vehicleUseQuery);
    }

    @Override
    public boolean update(VehicleUseDO vehicleUseDO) {
        return vehicleUseDao.update(vehicleUseDO);
    }
}
