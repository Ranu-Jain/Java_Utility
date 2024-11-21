package com.sbi.epay.logging.config;

import com.sbi.epay.logging.utility.LoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Class Name: WebConfig
 * *
 * Description: This class is for configure logging interceptor
 * *
 * Author: V1018400(Ranjan Kumar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    private final LoggingInterceptor loggingInterceptor;

    /**
     * Constructor initializing logging interceptor.
     * @param loggingInterceptor
     */
    public WebConfig(LoggingInterceptor loggingInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
    }

    /**
     * This method for registering interceptor.
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
    }
}