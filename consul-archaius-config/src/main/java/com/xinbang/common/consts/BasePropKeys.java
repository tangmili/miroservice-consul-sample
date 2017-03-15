package com.xinbang.common.consts;

/**
 * Created by JamesTang on 17/2/12.
 */
public interface BasePropKeys {
    String SERVICE_NAME = "moxie.cloud.service.name";
    String SERVICE_TAG = "service.tag";
    String SERVER_PORT = "server.port";
    String SWAGGER_ENABLED = "swagger.enabled";

    //consul相关
    String CONSUL_CHECK_URL = "moxie.cloud.consul.checks.http.url";
    String CONSUL_CHECK_INTERVAL = "moxie.cloud.consul.checks.http.interval";
    String CONSUL_CHECK_TTL = "moxie.cloud.consul.checks.ttl";
    String CONSUL_TOKEN = "consul.acl_token";

    //metrics相关
    String METRICS_STATSD_HOST = "metrics.statsd.host";
    String METRICS_STATSD_PORT = "metrics.statsd.port";

    //client相关
    String CLIENT_CONN_TIMEOUT = "client.default.conn.timeout";
    String CLIENT_READ_TIMEOUT = "client.default.read.timeout";
    String CLIENT_MAX_CONN = "client.default.max.conn";

    //业务相关
    String SERVICE_TOKEN = "service.token";
    String CIF_TAG = "cif.tag";
    String COMMON_SERVICE_TAG = "common-service.tag";
    String ORG_CODE = "org.code";
    String RATE_LIMIT_ENABLED = "ratelimit.enabled";
}
