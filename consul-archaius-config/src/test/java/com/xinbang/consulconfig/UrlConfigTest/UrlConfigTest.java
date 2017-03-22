/**
 * Truderal.com.cn Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.xinbang.consulconfig.UrlConfigTest;

import com.netflix.config.*;
import com.netflix.config.sources.URLConfigurationSource;

/**
 * @author militang
 * @version Id: UrlConfigTest.java, v 0.1 17/3/21 下午10:46 militang Exp $$
 */
public class UrlConfigTest {

    public static void main(String args[]) throws InterruptedException {

        AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler(1000, 1000, true);
        PolledConfigurationSource source = new URLConfigurationSource(
            "http://192.168.1.82/credit-Grp/marketting/blob/master/marketing-startup/src/main/resources/properties/datasource.properties");

        DynamicConfiguration dynamicConfig = new DynamicConfiguration(source, scheduler);

        ConfigurationManager.install(dynamicConfig);

        do {
            //              System.out.println( "eureka.name:"+dsp.get());
            System.out.println("eureka.name:" + dynamicConfig.getString("ds.driverClassName"));
            System.out.println("eureka.name:" + dynamicConfig.getString("ds.maxIdle"));
            /*String[] s = dynamicConfig.getStringArray("listprop");
            for (String a : s)
                System.out.println("eureka.name:" + a);
            System.out
                .println("eureka.name:"
                         + dynamicConfig.getString("log4j.logger.com.defonds.wms.module.server"));*/
            Thread.sleep(2000);
        } while (true);
    }
}