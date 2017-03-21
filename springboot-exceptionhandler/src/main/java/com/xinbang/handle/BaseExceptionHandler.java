package com.xinbang.handle;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import com.xinbang.constants.BaseErrorKeys;
import com.xinbang.service.BaseErrorContentService;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BaseExceptionHandler {
    private final ObjectMapper      objectMapper = new ObjectMapper();
    @Autowired
    private BaseErrorContentService baseErrorContentService;

    @PostConstruct
    public void init() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void validationError(MethodArgumentNotValidException e, HttpServletResponse resp) {
        //获取异常信息
        int index = 0;
        StringBuilder msg = new StringBuilder("");
        BindingResult result = e.getBindingResult();
        result.getFieldErrors().forEach(error -> {
            if (index != 0) {
                msg.append(",");
            }
            msg.append(error.getField()).append(":").append(error.getDefaultMessage());
        });
        log.error("异常:{}", msg);
        //this.writeErrorResponse(resp, this.errorContentByKey(BaseErrorKeys.METHOD_ARGUMENT_NOT_VALID, msg.toString()));
    }

    @ExceptionHandler(BindException.class)
    public void bindException(BindException e, HttpServletResponse resp) {
        //获取异常信息
        int index = 0;
        StringBuilder msg = new StringBuilder();
        e.getFieldErrors().forEach(error -> {
            if (index != 0) {
                msg.append(",");
            }
            msg.append(error.getField()).append(":").append(error.getDefaultMessage());
        });
        log.error("异常:{}", msg);
        //this.writeErrorResponse(resp, this.errorContentByKey(BaseErrorKeys.METHOD_ARGUMENT_NOT_VALID, msg.toString()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void missingServletRequestParameterException(MissingServletRequestParameterException e,
                                                        HttpServletResponse resp) {
        //ErrorContent errorContent = this.errorContentByKey(BaseErrorKeys.MISSING_SERVLET_REQUEST_PARAMETER, e.getParameterName());
        /*      log.error("异常:{}", errorContent.getMsg());
        this.writeErrorResponse(resp, errorContent);
        */ }

    @ExceptionHandler({ HttpMessageNotReadableException.class, TypeMismatchException.class })
    public void messageException(Exception e, HttpServletResponse resp) {
        // ErrorContent errorContent = this.errorContentByKey(BaseErrorKeys.BAD_REQUEST);
        if (e.getCause() != null && e.getCause() instanceof UnrecognizedPropertyException) {
            String propName = ((UnrecognizedPropertyException) e.getCause()).getPropertyName();
            log.error("无法识别的属性[{}]", propName);
            // errorContent = this.errorContentByKey(BaseErrorKeys.PARAM_UNRECOGNIZED, propName);
        } else if (e.getCause() != null && e.getCause() instanceof InvalidFormatException) {
            InvalidFormatException cause = ((InvalidFormatException) e.getCause());
            String value = cause.getValue().toString();
            String targetCls = cause.getTargetType().getSimpleName();
            log.error("值[{}]不是有效的[{}]类型", value, targetCls);
            // errorContent = this.errorContentByKey(BaseErrorKeys.PARAM_INVALID_FORMAT, value, targetCls);
        } else {
            //log.error(ExceptionUtils.getStackTrace(e));
        }
        //this.writeErrorResponse(resp, errorContent);
    }

    @ExceptionHandler({ HttpMediaTypeNotSupportedException.class,
                        HttpMediaTypeNotAcceptableException.class })
    public void badRequest(Exception e, HttpServletResponse resp) {
        //log.error(ExceptionUtils.getStackTrace(e));
        this.writeErrorResponse(resp,
            this.errorContentByKey(BaseErrorKeys.MEDIA_TYPE_NOT_SUPPORTED));
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public void illegalArgument(IllegalArgumentException e, HttpServletResponse resp) {
        log.error(e.getMessage());
        // this.writeErrorResponse(resp, this.errorContentByKey(BaseErrorKeys.ILLEGAL_ARGUMENT, e.getMessage()));
    }

    @ExceptionHandler({ SocketException.class })
    public void socketException(SocketException e, HttpServletResponse resp) {
        //  log.error(ExceptionUtils.getStackTrace(e));
        this.writeErrorResponse(resp, this.errorContentByKey(BaseErrorKeys.SOCKET_EXCEPTION));
    }

    @ExceptionHandler({ SocketTimeoutException.class })
    public void socketTimeoutException(SocketTimeoutException e, HttpServletResponse resp) {
        //   log.error(ExceptionUtils.getStackTrace(e));
        this.writeErrorResponse(resp, this.errorContentByKey(BaseErrorKeys.SOCKET_TIMEOUT));
    }

    //  @ExceptionHandler(ServerException.class)
    /*    public void serverException(ServerException e, HttpServletResponse resp) {
        ErrorContent errorContent = this.errorContentByKey(e.getErrorKey(), e.getArgs());
        log.error("异常:{}", errorContent.getMsg());
        this.writeErrorResponse(resp, errorContent);
    }*/

    @ExceptionHandler(Throwable.class)
    public void unknownError(Throwable e, HttpServletResponse resp) {
        log.error("发生未知的异常", e);
        this.writeErrorResponse(resp,
            this.errorContentByKey(BaseErrorKeys.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    private void writeErrorResponse(HttpServletResponse res, ErrorContent errorContent) {
        try {
            res.setCharacterEncoding(StandardCharsets.UTF_8.name());
            res.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
            res.setStatus(errorContent.getHttpCode());
            objectMapper.writeValue(res.getOutputStream(), errorContent.exceptionResp());
        } catch (IOException e1) {
            log.error("Can not write to the response output stream: {}", e1);
        }
    }

    private ErrorContent errorContentByKey(String key, String... args) {

        return baseErrorContentService.errorContentByKey(key, args);
    }
}
