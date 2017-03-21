package com.xinbang.handle;

import com.fasterxml.jackson.annotation.JsonProperty;
//import com.moxie.commons.BaseJsonUtils;

import lombok.Data;

/**
 * Created by wangyanbo on 16/11/16.
 */
@Data
public class ExceptionResp {
    @JsonProperty("correlation_id")
    private String correlationId;
    private String code;
    private String msg;

    public ExceptionResp() {
    }

    public ExceptionResp(String code, String msg, String correlationId) {
        this.code = code;
        this.msg = msg;
        this.correlationId = correlationId;
    }

 /*   public String toString() {
        return BaseJsonUtils.writeValue(this);
    }*/
}
