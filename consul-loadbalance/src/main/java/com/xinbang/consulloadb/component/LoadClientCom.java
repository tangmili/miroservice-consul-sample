/**
 * Truderal.com.cn Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.xinbang.consulloadb.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URI;

/**
 * @author militang
 * @version Id: LoadClientCom.java, v 0.1 17/3/17 下午7:21 militang Exp $$
 */
@Component
@Slf4j
public class LoadClientCom {

    @Autowired
    private LoadBalancerClient loadBalancerClient;



    @Autowired
    private DiscoveryClient discoveryClient;

    public void queryService(String serviceName) {


    }

    public URI getRestUrl(String serviceId, String fallbackUri) {
        URI uri = null;
        try {

            ServiceInstance instance = loadBalancerClient.choose(serviceId);
            uri = instance.getUri();
            log.info(uri.toString());
        } catch (RuntimeException e) {
            uri = URI.create(fallbackUri);
        }

        return uri;
    }

}