package com.alpha.manager.impl;

import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.constans.YunUtil;
import com.alpha.dao.MaintainDao;
import com.alpha.domain.MaintainDO;
import com.alpha.domain.MaintainSumDO;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.MaintainManager;
import com.alpha.manager.VehicleManager;
import com.alpha.query.MaintainQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Component("maintainManager")
public class MaintainManagerImpl implements MaintainManager {

    @Resource
    private MaintainDao maintainDao;
    @Resource
    private VehicleManager vehicleManager;

    @Override
    public boolean insert(MaintainDO maintainDO) {
        return maintainDao.insert(maintainDO);
    }

    @Override
    public List<MaintainDO> query(MaintainQuery maintainQuery) {
        return maintainDao.query(maintainQuery);
    }

    @Override
    public MaintainDO queryById(int id) {
        MaintainQuery maintainQuery = new MaintainQuery();
        maintainQuery.setId(id);
        List<MaintainDO> maintainDOList = maintainDao.query(maintainQuery);
        return (maintainDOList == null || maintainDOList.size() == 0) ? null : maintainDOList.get(0);
    }

    @Override
    public int count(MaintainQuery maintainQuery) {
        return maintainDao.count(maintainQuery);
    }

    @Override
    public boolean update(MaintainDO maintainDO) {
        return maintainDao.update(maintainDO);
    }

    @Override
    @Transactional(rollbackForClassName="Exception")
    public boolean sendMaintain(MaintainDO maintainDO) throws Exception  {
        if (!maintainDao.update(maintainDO)) {
            return false;
        }
        MaintainDO maintainDOInDB = queryById(maintainDO.getId());
        VehicleDO vehicleDO = vehicleManager.queryByVehicleNO(maintainDOInDB.getVehicleNO());
        vehicleDO.setApplicationId(maintainDOInDB.getId());
        vehicleDO.setStatus(SystemConstant.VEHICLE_MAINTAIN);
        if (!vehicleManager.update(vehicleDO)) {
            throw new Exception("更新车辆状态失败");
        }
        YunUtil.sendMaintainVerifyPass(maintainDOInDB);
        return true;
    }

    @Override
    @Transactional(rollbackForClassName="Exception")
    public boolean pickUpMaintain(MaintainDO maintainDO) throws Exception {
        MaintainDO maintainDOInDB = queryById(maintainDO.getId());
        VehicleDO vehicleDO = vehicleManager.queryByVehicleNO(maintainDOInDB.getVehicleNO());
        if (vehicleDO == null) {
            return false;
        }
        if (!maintainDao.update(maintainDO)) {
            return false;
        }
        Date nextMaintainDate = CalendarUtil.addDate(maintainDO.getActualPickUpDate(), 120);
        int nextMaintainMile = maintainDO.getMile() + 4000;
        int mile = (vehicleDO.getMile() == null ? 0 : vehicleDO.getMile());
        if  ("维修".equals(maintainDOInDB.getType()) && maintainDO.getMile() > mile) {
            vehicleDO.setMile(maintainDO.getMile());
            if (!vehicleManager.update(vehicleDO)) {
                throw new Exception("更新车辆状态失败");
            }
        }
        int maintainMile = (vehicleDO.getMaintainMile() == null ? 0 : vehicleDO.getMaintainMile());
        if ("保养".equals(maintainDOInDB.getType()) && (nextMaintainDate.after(vehicleDO.getMaintainDate())
                || nextMaintainMile > maintainMile || maintainDO.getMile() > mile)) {
            vehicleDO.setMaintainDate(nextMaintainDate);
            vehicleDO.setMaintainMile(nextMaintainMile);
            vehicleDO.setMile(maintainDO.getMile());
            if (!vehicleManager.update(vehicleDO)) {
                throw new Exception("更新车辆状态失败");
            }
        }
        if (SystemConstant.VEHICLE_MAINTAIN.equals(vehicleDO.getStatus()) && vehicleDO.getApplicationId() != null
                && maintainDOInDB.getId().intValue() == vehicleDO.getApplicationId()) {
            vehicleDO.setStatus(SystemConstant.VEHICLE_CAN_USE);
            vehicleDO.setApplicationId(0);
            if (!vehicleManager.update(vehicleDO)) {
                throw new Exception("更新车辆状态失败");
            }
        }
        return true;
    }

    @Override
    public List<MaintainSumDO> queryGroupByVehicle(MaintainQuery maintainQuery) {
        return maintainDao.queryGroupByVehicle(maintainQuery);
    }

    @Override
    public int countGroupByVehicle(MaintainQuery maintainQuery) {
        return maintainDao.countGroupByVehicle(maintainQuery);
    }
}
