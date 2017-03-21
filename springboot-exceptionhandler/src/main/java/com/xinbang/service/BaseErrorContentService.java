package com.xinbang.service;

import com.xinbang.handle.ErrorContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by wangyanbo on 17/2/20.
 */
@Slf4j
@Service
public class BaseErrorContentService {
    private final String DEFAULT_ERROR = "系统异常,请稍候重试!";
    //@Autowired
    //private BaseEnvironment env;

    public ErrorContent errorContentByKey(String key, String... args) {
        //String errorContent = env.getError(key);
        return null;
        /* if (StringUtils.isBlank(errorContent)) {
            log.error("未找到error key[{}]对应的配置", key);
            return new ErrorContent("5000", DEFAULT_ERROR, 500);
        }
        return new ErrorContent(StringUtils.substringBefore(errorContent, ";"),
                this.getErrorMsg(StringUtils.substringBetween(errorContent, ";", ";"), args),
                Integer.valueOf(StringUtils.substringAfterLast(errorContent, ";")));*/
    }

    private String getErrorMsg(String msg, String... args) {
        for (String arg : args) {
            if (arg != null) {
                msg = msg.replaceFirst("\\{\\}", arg);
            }
        }
        return msg;
    }
}
