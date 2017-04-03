package com.alpha;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by taoxiang on 2017/3/29.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:SqlMapClientTemplate.xml","classpath:biz-base.xml"})

public class ITestBase extends AbstractJUnit4SpringContextTests {

    @Test
    public void test() {
        System.out.println("test is ok");
    }
}
