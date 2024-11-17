package com.sbi.epay.logging.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Map;

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
public class LoggerFactoryUtility {

    /**
     * private constructor tp prevent instantiation
     */
    private LoggerFactoryUtility() {
    }

    /**
     * Static method to return a loggerUtility instance for a given class
     *
     * @param clazz
     * @return Logger
     */
    public static LoggerUtility getLogger(Class<?> clazz) {
        Logger logger=LoggerFactory.getLogger(clazz);//gets SL4J's logger
        return new LoggerUtility(logger);//wrap in LoggerUtility
    }

    /**
     * Static method to return by name
     * @param name
     * @return
     */
    public static LoggerUtility getLogger(String name) {
        Logger logger=LoggerFactory.getLogger(name);
        return new LoggerUtility(logger);
    }

    /**
     * Set multiple key-value pairs in the MDC context using a Map.
     * *
     * Note: This method is created for future to set the MDCContext using Map with multiple params at once.
     * @param contextMap
     */
    public static void setMDCContext(Map<String, String> contextMap) {
        if (contextMap != null) {
            contextMap.forEach(MDC::put);
        } else {
            throw new IllegalArgumentException("Invalid inputs");
        }
    }

    /**
     * Set key-value pair in the MDC context.
     * @param key
     * @param value
     */
    public static void putMDC(String key, String value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("MDC key and value cannot be null");
        }
        MDC.put(key, value);
    }

    /**
     * Clear all entries from the MDC context.
     */
    public static void clearMDCContext() {
        MDC.clear();
    }

}
