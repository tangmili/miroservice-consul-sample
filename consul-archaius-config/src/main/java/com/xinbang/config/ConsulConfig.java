package com.xinbang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.ecwid.consul.v1.ConsulClient;
import com.xinbang.common.consts.BaseConst;
import com.xinbang.common.consts.BasePropKeys;
import com.xinbang.consulconfig.ConsulPropertySourceLocator;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@PropertySource({ "classpath:bootstrap.properties" })
public class ConsulConfig {
    @Autowired
    private Environment env;

    @Value("${spring.cloud.consul.host}")
    private String      consulagentip = "http://localhost";

    @Value("${spring.cloud.consul.port}")
    private int         consulagentipport;

    @Bean
    public ConsulClient consulClient() {
        if (isLocal()) {
            return null;
        }
        return new ConsulClient(consulagentip, consulagentipport);
    }
    /*
    
    @Bean
    @ConditionalOnMissingBean
    public ConsulConfigProperties consulConfigProperties() {
        return new ConsulConfigProperties();
    }
    
    @Bean
    public ConsulPropertySourceLocator consulPropertySource() {
        return new ConsulPropertySourceLocator();
    }
    */

    @Bean
    public ConsulPropertySourceLocator consulPropertySource() {
        return new ConsulPropertySourceLocator(consulClient());
    }

    private boolean isLocal() {
        return BaseConst.TAG_LOCAL.equalsIgnoreCase(env.getProperty(BasePropKeys.SERVICE_TAG));
    }
}
