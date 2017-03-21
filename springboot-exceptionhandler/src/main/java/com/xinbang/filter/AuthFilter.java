package com.xinbang.filter;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.net.HttpHeaders;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by JamesTang on 16/9/13.
 */
@Slf4j
@Component
public class AuthFilter extends HandlerInterceptorAdapter {
    @Autowired
    private Environment env;
    @Autowired
/*    private CifGateWayClient cifGateWayClient;*/

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if (handler instanceof HandlerMethod && !HttpMethod.OPTIONS.name().equalsIgnoreCase(req.getMethod())) {
            Method method = ((HandlerMethod) handler).getMethod();
            try {
                this.checkAuthScopes(method, req);
            } finally {
                resp.setHeader(org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            }
        }

        return true;
    }

    private void checkAuthScopes(Method method, HttpServletRequest req) {
/*        AuthScope authScope = method.getAnnotation(AuthScope.class);
        if (authScope == null) {
            return;
        }
        //获取token
        String jwtToken = this.getToken(req, AuthUtil.BEARER_MECHANISM);
        try {
            TokenVerifyRsp verifyRsp = cifGateWayClient.verifyToken(new TokenVerifyReq(AuthUtil.BEARER_MECHANISM + jwtToken));
            if (verifyRsp != null && verifyRsp.getCode() == 0) {
                AuthRole role = Arrays.stream(authScope.value())
                        .filter(authRole -> StringUtils.equalsIgnoreCase(authRole.name(), verifyRsp.getTokenType()))
                        .findAny().orElse(null);
                if (role != null) {x
                    if (StringUtils.equalsIgnoreCase(verifyRsp.getTokenType(), "service")) {
                        MDC.put(AuthMdcKeys.SERVICE_FROM, verifyRsp.getPrincipal());
                    } else if (StringUtils.equalsIgnoreCase(verifyRsp.getTokenType(), "tenant")) {
                        MDC.put(AuthMdcKeys.ORG_CODE, verifyRsp.getOrgCode());
                    } else if (StringUtils.equalsIgnoreCase(verifyRsp.getTokenType(), "user")) {
                        MDC.put(AuthMdcKeys.ORG_CODE, verifyRsp.getOrgCode());
                        MDC.put(AuthMdcKeys.USER_ID, verifyRsp.getPrincipal());
                        MDC.put(AuthMdcKeys.ACCOUNT, verifyRsp.getAccount());
                    }
                }
                return;
            } else if (verifyRsp != null) {
                log.error("token校验失败:" + verifyRsp.getDescription());
            }
        } catch (Throwable e) {
            if(e instanceof ClientException) {
                if(((ClientException) e).getStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
                    log.error("无service token权限调用cif validate接口");
                    throw ServerException.fromKey(BaseErrorKeys.INTERNAL_SERVER_ERROR);
                }
            }
            log.error("调用cif校验token异常", e);
        }
        throw ServerException.fromKey(BaseErrorKeys.UNAUTHORIZED);*/
    }

    private String getToken(HttpServletRequest request, String prefix) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
      /*  if (Strings.isBlank(authHeader)) {
            log.error("请求缺少头部:" + HttpHeaders.AUTHORIZATION);
            throw ServerException.fromKey(BaseErrorKeys.UNAUTHORIZED);
        }

        int index = authHeader.indexOf(" ");
        if (index == -1) {
            log.error("token[{}]缺少token类型", authHeader);
            throw ServerException.fromKey(BaseErrorKeys.UNAUTHORIZED);
        } else {
            String tokenType = authHeader.substring(0, index);
            if (!tokenType.equalsIgnoreCase(prefix.trim())) {
                log.error("token类型不匹配 期望[{}], 实际[{}]", prefix, tokenType);
                throw ServerException.fromKey(BaseErrorKeys.UNAUTHORIZED);
            }
            return authHeader.substring(index).trim();
        }*/
      return  null;
    }
}
