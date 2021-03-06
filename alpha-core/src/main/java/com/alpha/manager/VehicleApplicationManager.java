package com.alpha.manager;

import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleApplicationSumDO;
import com.alpha.query.VehicleApplicationQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/6.
 */
public interface VehicleApplicationManager {

    boolean insert(VehicleApplicationDO vehicleApplicationDO);

    List<VehicleApplicationDO> query(VehicleApplicationQuery vehicleApplicationQuery);

    List<Integer> queryByApplicantId(int applicantId);

    int count(VehicleApplicationQuery vehicleApplicationQuery);

    boolean update(VehicleApplicationDO vehicleApplicationDO);

    List<VehicleApplicationSumDO> queryGroupByDepartment(VehicleApplicationQuery vehicleApplicationQuery);

    int countGroupByDepartment(VehicleApplicationQuery vehicleApplicationQuery);
}
