package com.alpha.manager;

import com.alpha.domain.PartnerDepartmentDO;
import com.alpha.query.PartnerDepartmentQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/5/9.
 */
public interface PartnerDepartmentManager {

    boolean insert(PartnerDepartmentDO partnerDepartmentDO);

    List<PartnerDepartmentDO> query(PartnerDepartmentQuery partnerDepartmentQuery);

    int count(PartnerDepartmentQuery partnerDepartmentQuery);

    boolean update(PartnerDepartmentDO partnerDepartmentDO);
}
