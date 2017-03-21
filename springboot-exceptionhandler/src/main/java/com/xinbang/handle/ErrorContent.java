package com.xinbang.handle;

import org.slf4j.MDC;

import lombok.Data;
//import moxie.cloud.service.common.util.ContextConstants;

@Data
public class ErrorContent {
    private String code;
    private String msg;
    private Integer httpCode;

    public ErrorContent(String code, String msg, Integer httpCode) {
        this.code = code;
        this.msg = msg;
        this.httpCode = httpCode;
    }

    public ExceptionResp exceptionResp() {
        return new ExceptionResp(this.getCode(), this.getMsg(), ""/*MDC.get(ContextConstants.CORRELATION_ID_MDC_KEY)*/);
    }
}
