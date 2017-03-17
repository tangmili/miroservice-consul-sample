/**
 * Truderal.com.cn Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.xinbang.consulloadb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author militang
 * @version Id: App.java, v 0.1 17/3/14 下午10:51 militang Exp $$
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
//@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"com.xinbang"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

    }

}