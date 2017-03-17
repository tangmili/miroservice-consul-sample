/**
 * Truderal.com.cn Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.xinbang.consulconfig.consulconfigtest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import com.ecwid.consul.v1.ConsulClient;
import com.xinbang.consulconfig.ConsulPropertySourceLocator;
import com.xinbang.consulconfig.testbase.SpringBootTestBase;

import lombok.extern.slf4j.Slf4j;

/**
 * @author militang
 * @version Id: startTest.java, v 0.1 17/3/14 下午10:58 militang Exp $$
 */
@Slf4j
public class ConsulConfigReadTest extends SpringBootTestBase {

    @Autowired
    Environment                         env;

    @Value("${moxie.cloud.logging.logstash.mode}")
    private String                      ddd;

    @Autowired
    private ConsulPropertySourceLocator consulPropertySourceLocator;

    @Test
    public void runtest() {
        log.info("from evn moxie.cloud.logging.logstash.mode value {}", ddd);
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("from evn moxie.cloud.logging.logstash.mode value {}",
                env.getProperty("moxie.cloud.logging.logstash.mode"));
        }
    }
}