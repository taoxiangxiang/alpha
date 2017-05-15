package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.SystemConstant;
import com.alpha.dao.VerifyRecordDao;
import com.alpha.domain.DepartmentDO;
import com.alpha.domain.VerifyRecordDO;
import com.alpha.query.DepartmentQuery;
import com.alpha.query.VerifyRecordQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class VerifyRecordManagerTest extends ITestBase {

    @Resource
    private VerifyRecordDao verifyRecordDao;

    @Test
    public void testInsert() {
        VerifyRecordDO verifyRecordDO = new VerifyRecordDO();
        verifyRecordDO.setVerifyId(1);
        verifyRecordDO.setVerifyName("");
        verifyRecordDO.setApplicationEvent("测试时间");
        verifyRecordDO.setApplicationId(2);
        verifyRecordDO.setResult("同意");
        verifyRecordDO.setRemark("备注");
        boolean res = verifyRecordDao.insert(verifyRecordDO);
        System.out.println("res=" + res);
    }

    @Test
    public void testQuery() {
        VerifyRecordQuery verifyRecordQuery = new VerifyRecordQuery();
//        verifyRecordQuery.setApplicationId(2);
        List<VerifyRecordDO> list = verifyRecordDao.query(verifyRecordQuery);
        int number = verifyRecordDao.count(verifyRecordQuery);
        System.out.println("number=" + number);
        System.out.println("res=" + JSON.toJSONString(list));
    }
}
