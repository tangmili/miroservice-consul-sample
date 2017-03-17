/**
 * Truderal.com.cn Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.xinbang.consulloadb.com;

import com.xinbang.consulloadb.component.LoadClientCom;
import com.xinbang.consulloadb.component.RestCallcom;
import com.xinbang.consulloadb.testbase.SpringBootTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;

/**
 * @author militang
 * @version Id: LoadClientComTest.java, v 0.1 17/3/17 下午7:36 militang Exp $$
 */
@Slf4j
public class LoadClientComTest extends SpringBootTestBase {

    @Autowired
    private LoadClientCom loadClientCom;

    @Autowired
    private RestCallcom restCallcom;

    @Test
    public void queryload() {
        URI url = loadClientCom.getRestUrl("kafka-monitor-query", "");
        log.info(url.toString());

    }

    @Test
    public  void accountquery(){
        restCallcom.accountting("kafka-monitor-query");

    }

}