/**
 * Truderal.com.cn Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.xinbang.consulconfig;

import java.util.Iterator;

import org.springframework.core.env.MapPropertySource;

import com.ecwid.consul.v1.ConsulClient;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.netflix.config.*;

/**
 * @author militang
 * @version Id: ConsulPropertySource.java, v 0.1 17/3/14 下午9:34 militang Exp $$
 */
public class ConsulPropertySource extends MapPropertySource {

    private DynamicConfiguration props;

    /**
     * 从consul上读取属性并存入netflix config
     */
    public ConsulPropertySource(String configKey, String token, ConsulClient consul) {
        super(configKey, Maps.newHashMap());
        props = getDynamicConfiguration(configKey, token, consul);
        //ConfigurationManager.install(props); // TODO: 17/2/24  check

        Iterator it = props.getKeys();
        while (it.hasNext()) {
            String kk = (String) it.next();
            this.source.put(kk, props.getProperty(kk));
        }
    }

    /**
     * 将属性存入PropertySource
     */
    private DynamicConfiguration getDynamicConfiguration(String configKey, String token,
                                                         ConsulClient consul) {
        PolledConfigurationSource polledSource = new ConsulConfigurationSource(configKey, consul,
            token);
        //defaul 30s
        return new DynamicConfiguration(polledSource, new FixedDelayPollingScheduler());

        //scheduler = new FixedDelayPollingScheduler(10000, refreshInterval * 1000, false);

    }

    @Override
    public Object getProperty(String name) {
        return props.getProperty(name);
    }

    @Override
    public String[] getPropertyNames() {
        return Iterators.toArray((Iterator<String>) props.getKeys(), String.class);
        //        Set<String> keys = this.source.keySet();
        //        return keys.toArray(new String[0]);
    }

}