/**
 * B2bFinace Alibab.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.xinbang.consulloadb.testbase;

import com.xinbang.consulloadb.App;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;



/**
 * Sring Unit Test Base Class
 *
 * @author jamestang
 * @version Id: SpringTestBase.java, v 0.1 16/4/28 上午9:39 jamestang Exp $$
 */

@SpringBootTest(classes = App.class)
@ComponentScan("com.xinbang")
@EnableConfigurationProperties
//@PropertySource("classpath:application.properties")
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = CreditLimitService.class)
@WebAppConfiguration
//@EnableHSF
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class SpringBootTestBase {
    @BeforeClass
    public static void initparam() {
        //System.getProperties().put(BasePropKeys.SERVICE_NAME, "agreement");
        //System.getProperties().put(BasePropKeys.SERVICE_TAG, "test");
    }

}