/**
 * Truderal.com.cn Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.xinbang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author militang
 * @version Id: App.java, v 0.1 17/3/14 下午10:51 militang Exp $$
 */
@SpringBootApplication
@EnableAutoConfiguration
//@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"com.xinbang"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

    }

}