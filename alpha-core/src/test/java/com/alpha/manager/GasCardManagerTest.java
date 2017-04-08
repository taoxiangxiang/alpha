package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.GasCardDO;
import com.alpha.query.GasCardQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class GasCardManagerTest extends ITestBase {


    @Resource
    private GasCardManager gasCardManager;

    @Test
    public void testInsert() {
        GasCardDO gasCardDO = new GasCardDO();
        gasCardDO.setGasCardNO("测试油卡编号");
        gasCardDO.setGasCardType("测试类型");
        gasCardDO.setAmount(100.0);
        gasCardDO.setStatus(SystemConstant.GAS_CARD_ON_LINE);
        boolean res = gasCardManager.insert(gasCardDO);
        System.out.println(res);
    }

    @Test
    public void testQuery() {
        GasCardQuery gasCardQuery = new GasCardQuery();
        gasCardQuery.setPage(1);
        gasCardQuery.setPageSize(100);
        List<GasCardDO> list = gasCardManager.query(gasCardQuery);
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void testUpdate() {
        GasCardDO gasCardDO = new GasCardDO();
        gasCardDO.setId(1);
        gasCardDO.setGasCardNO("测试油卡编号1");
        gasCardDO.setGasCardType("测试类型1");
        gasCardDO.setAmount(100.1);
        boolean res = gasCardManager.update(gasCardDO);
        System.out.println(res);
    }

    @Test
    public void testDel() {
        GasCardDO gasCardDO = new GasCardDO();
        gasCardDO.setId(1);
        gasCardDO.setStatus(SystemConstant.GAS_CARD_DELETE);
        boolean res = gasCardManager.update(gasCardDO);
        System.out.println(res);
    }
}
