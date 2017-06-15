package com.alpha.manager.impl;

import com.alpha.dao.InsuranceDao;
import com.alpha.domain.InsuranceDO;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.InsuranceManager;
import com.alpha.manager.VehicleManager;
import com.alpha.query.InsuranceQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Component("insuranceManager")
public class InsuranceManagerImpl implements InsuranceManager {

    @Resource
    private InsuranceDao insuranceDao;
    @Resource
    private VehicleManager vehicleManager;

    @Override
    @Transactional(rollbackForClassName="Exception")
    public boolean insert(InsuranceDO insuranceDO) throws Exception{
        VehicleDO vehicleDO = vehicleManager.queryByVehicleNO(insuranceDO.getVehicleNO());
        if (vehicleDO == null) {
            return false;
        }
        if (!insuranceDao.insert(insuranceDO)) {
            return false;
        }
        if (insuranceDO.getInsuranceEndDate().after(vehicleDO.getInsuranceDate())) {
            vehicleDO.setInsuranceDate(insuranceDO.getInsuranceEndDate());
            if (!vehicleManager.update(vehicleDO)) {
                throw new Exception("数据库写入失败");
            }
        }
        return true;
    }

    @Override
    public List<InsuranceDO> query(InsuranceQuery insuranceQuery) {
        return insuranceDao.query(insuranceQuery);
    }

    @Override
    public int count(InsuranceQuery insuranceQuery) {
        return insuranceDao.count(insuranceQuery);
    }

    @Override
    @Transactional(rollbackForClassName="Exception")
    public boolean update(InsuranceDO insuranceDO) throws Exception {
        VehicleDO vehicleDO = vehicleManager.queryByVehicleNO(insuranceDO.getVehicleNO());
        if (vehicleDO == null) {
            return false;
        }
        if (!insuranceDao.update(insuranceDO)) {
            return false;
        }
        if (insuranceDO.getInsuranceEndDate().after(vehicleDO.getInsuranceDate())) {
            vehicleDO.setInsuranceDate(insuranceDO.getInsuranceEndDate());
            if (!vehicleManager.update(vehicleDO)) {
                throw new Exception("数据库写入失败");
            }
        }
        return true;
    }
}
