/**
 * Truderal.com.cn Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.xinbang.consulloadb.component;

import com.google.common.collect.Maps;
import com.sun.jndi.toolkit.url.Uri;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

/**
 * @author militang
 * @version Id: RestCallcom.java, v 0.1 17/3/17 下午7:19 militang Exp $$
 */
@Component
@Slf4j
public class RestCallcom {

    @Autowired
    RestTemplate          restTemplate;

    @Autowired
    private LoadClientCom loadClientCom;

    public void accountting(String serviceName) {

        Map<String, String> paramap = Maps.newHashMap();
        paramap.put("configNo", "213");
       // URI uri = loadClientCom.getRestUrl(serviceName, "");
      //  String requrl = uri.toString() + "/kafka-monitor-query/api/v1/monitors/configs/";
        String requrl = "http://kafka-monitor-query/kafka-monitor-query/api/v1/monitors/configs/";

        String result = restTemplate.getForObject(requrl, String.class,paramap);

        log.info(result);
    }

}