package com.xinbang.consulconfig;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import com.ecwid.consul.v1.ConsulClient;
import com.xinbang.common.consts.BaseConst;
import com.xinbang.common.consts.BasePropKeys;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsulPropertySourceLocator implements PropertySourceLocator {
    private ConsulClient consul;

    public ConsulPropertySourceLocator(ConsulClient consul) {
        this.consul = consul;

    }

    @Override
    public PropertySource<?> locate(Environment environment) {
        if (!(environment instanceof ConfigurableEnvironment)) {
            return null;
        }
        ConfigurableEnvironment env = (ConfigurableEnvironment) environment;
        String serviceName = env.getProperty(BasePropKeys.SERVICE_NAME);
        String serviceTag = env.getProperty(BasePropKeys.SERVICE_TAG);

        if (BaseConst.TAG_LOCAL.equalsIgnoreCase(serviceTag)) {
            //                AbstractConfiguration config = ConfigurationManager.getConfigInstance();
            //                Properties properties = new Properties();
            //                try {
            //                    properties.load(this.getClass().getResourceAsStream("/application.properties"));
            //                    for (Object k : properties.keySet()) {
            //                        config.addProperty((String) k, properties.getProperty((String) k));
            //                    }
            //                } catch (IOException e) {
            //                    log.error("Failed to load application.properties");
            //                    throw new CoreRuntimeException(e);
            //                }
            return null;
        }

        env.getPropertySources().remove("applicationConfigurationProperties");
        String consulKey = String.format("service/%s/%s/config", serviceName, serviceTag);
        try {
            //ConsulClient consul = new ConsulClient("http://localhost", 8500);
            return new ConsulPropertySource(consulKey, env.getProperty(BasePropKeys.CONSUL_TOKEN),
                consul);
        } catch (Throwable e) {
            throw new RuntimeException("初始化Consul PropertySource异常", e);
        }

    }
}