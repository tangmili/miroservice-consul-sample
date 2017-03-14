/**
 * Truderal.com.cn Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.xinbang.consulconfig;

import java.io.StringReader;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Base64Utils;

import com.ecwid.consul.v1.ConsistencyMode;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;
import com.xinbang.common.consts.BaseConst;

import lombok.extern.slf4j.Slf4j;

/**
 * 指定archaius读取配置的源头
 * @author militang
 * @version Id: ConsulConfigurationSource.java, v 0.1 17/3/14 下午8:54 militang Exp $$
 */

@Slf4j
public class ConsulConfigurationSource implements PolledConfigurationSource {

    private ConsulClient consul;

    private String       keyName;

    private String       token;

    private long         keyIndex = 0l;

    public ConsulConfigurationSource(String keyName, ConsulClient consul, String token) {
        this.consul = consul;
        this.keyName = keyName;
        this.token = token;
    }

    /**
     * 默认情况下，每隔60s，该方法会执行一次
      */
    @Override
    public PollResult poll(boolean initial, Object checkPoint) throws Exception {
        Response<GetValue> optValue = consul.getKVValue(keyName,
            Strings.isNullOrEmpty(token) ? null : token,
            new QueryParams(ConsistencyMode.CONSISTENT));
        GetValue value = optValue.getValue();
        log.info("consul:key is [{}] value is  [{}]内容加载", keyName, value);
        if (null == value) {
            if (StringUtils.containsIgnoreCase(keyName, BaseConst.CONFIG_NODE_NAME)) {
                log.error("无法从consul中获取服务配置[key:{}]", keyName);
            }
            return PollResult.createFull(Maps.newHashMap());
        }

        long crtIndex = value.getModifyIndex();
        if (keyIndex == crtIndex && !initial) {
            // as same as last check point
            return PollResult.createIncremental(null, null, null, value.getFlags());
        }
        keyIndex = crtIndex;
        String propString = decodeBase64(value.getValue());
        Map<String, Object> map = Maps.newHashMap();
        if (propString == null) {
            return PollResult.createFull(map);
        }
        Properties properties = new Properties();
        properties.load(new StringReader(propString));
        properties.entrySet().forEach(e -> map.put((String) e.getKey(), e.getValue()));
        PollResult.createFull(map);
        return PollResult.createFull(map);

        //Consul consul = Consul.builder().build();
        /*   KeyValueClient kvClient = consul.keyValueClient();
        Optional<String> kvOpt = kvClient.getValueAsString(keyName);
        String kvStr = StringUtils.EMPTY;
        if (kvOpt.isPresent()) {
            kvStr = kvOpt.get();
        }
        
        Properties props = new Properties();
        props.load(new StringReader(kvStr));//String->Properties
        
        Map<String, Object> propMap = Maps.newHashMap();
        for (Object key : props.keySet()) {
            propMap.put((String) key, props.get(key));
        }
        return PollResult.createFull(propMap);*/
    }

    private String decodeBase64(String value) {
        if (value == null) {
            return null;
        }
        return new String(Base64Utils.decodeFromString(value), Charsets.UTF_8);
    }
}