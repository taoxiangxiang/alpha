package com.alpha.manager.impl;

import com.alpha.dao.VehicleApplicationDao;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleApplicationSumDO;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.query.VehicleApplicationQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/6.
 */
@Component("vehicleApplicationManager")
public class VehicleApplicationManagerImpl implements VehicleApplicationManager {

    @Resource
    private VehicleApplicationDao vehicleApplicationDao;

    @Override
    public boolean insert(VehicleApplicationDO vehicleApplicationDO) {
        return vehicleApplicationDao.insert(vehicleApplicationDO);
    }

    @Override
    public List<VehicleApplicationDO> query(VehicleApplicationQuery vehicleApplicationQuery) {
        return vehicleApplicationDao.query(vehicleApplicationQuery);
    }

    @Override
    public List<Integer> queryByApplicantId(int applicantId) {
        VehicleApplicationQuery vehicleApplicationQuery = new VehicleApplicationQuery();
        vehicleApplicationQuery.setApplicantId(applicantId);
        List<VehicleApplicationDO> vehicleApplicationDOList = vehicleApplicationDao.query(vehicleApplicationQuery);
        List<Integer> applicationIdList = new ArrayList<Integer>();
        if (vehicleApplicationDOList == null) {
            return applicationIdList;
        }
        for (VehicleApplicationDO vehicleApplicationDO : vehicleApplicationDOList) {
            applicationIdList.add(vehicleApplicationDO.getId());
        }
        return applicationIdList;
    }

    @Override
    public int count(VehicleApplicationQuery vehicleApplicationQuery) {
        return vehicleApplicationDao.count(vehicleApplicationQuery);
    }

    @Override
    public boolean update(VehicleApplicationDO vehicleApplicationDO) {
        return vehicleApplicationDao.update(vehicleApplicationDO);
    }

    @Override
    public List<VehicleApplicationSumDO> queryGroupByDepartment(VehicleApplicationQuery vehicleApplicationQuery) {
        return vehicleApplicationDao.queryGroupByDepartment(vehicleApplicationQuery);
    }

    @Override
    public int countGroupByDepartment(VehicleApplicationQuery vehicleApplicationQuery) {
        return vehicleApplicationDao.count(vehicleApplicationQuery);
    }
}
