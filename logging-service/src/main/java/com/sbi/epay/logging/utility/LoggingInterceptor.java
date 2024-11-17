package com.sbi.epay.logging.utility;

import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Class Name: LoggerFactory
 * *
 * Description: This class is for getting logger instances
 * *
 * Author: V1018400(Ranjan Kumar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private final LoggerFactoryUtility loggerFactory;

    /**
     * Initializing loggerFactory object
     * @param loggerFactory
     */
    public LoggingInterceptor(LoggerFactoryUtility loggerFactory) {
        this.loggerFactory = loggerFactory;
    }

    /**
     * This method is using for prehandle requests with MDC context
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return boolean : returning true if corelation id present in request otherwise
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
           loggerFactory.putMDC("userId", request.getHeader("userId") != null ? request.getHeader("userId") : "anonymous");
           loggerFactory.putMDC("correlationId", request.getHeader("X-Request-ID") != null ? request.getHeader("X-Request-ID") : UUID.randomUUID().toString().toUpperCase().replace(".", ""));
        return true;
    }

    /**
     * This method is using for Clear MDC after request processing
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler the handler (or {@link HandlerMethod}) that started asynchronous
     * execution, for type and/or instance examination
     * @param ex any exception thrown on handler execution, if any; this does not
     * include exceptions that have been handled through an exception resolver
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.clear();
    }

}
