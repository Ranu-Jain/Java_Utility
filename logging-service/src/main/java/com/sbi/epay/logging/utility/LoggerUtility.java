package com.sbi.epay.logging.utility;

import org.slf4j.Logger;

/**
 * Class Name: LoggerUtility
 * *
 * Description: This class is for getting logger method
 * *
 * Author: V1018400(Ranjan Kumar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
public class LoggerUtility {
    private final Logger logger;

    /**
     * Constructor take an SL4J logger and wraps it
     *
     * @param logger
     */
    public LoggerUtility(Logger logger) {
        this.logger = logger;
    }

    /**
     * This method for warning with param massage
     *
     * @param msg
     */
    public void trace(String msg) {
        logger.trace(msg);
    }

    /**
     * This method for warning with param massage and format
     *
     * @param format
     * @param arguments
     */
    public void trace(String format, Object... arguments) {
        logger.trace(format, arguments);
    }

    /**
     * This method for warning with param massage format with exception throwable
     *
     * @param format
     * @param throwable
     */
    public void trace(String format, Throwable throwable) {
        logger.trace(format, throwable);
    }

    /**
     * This method for warning with param massage
     *
     * @param msg
     */
    public void debug(String msg) {
        logger.debug(msg);
    }

    /**
     * This method for warning with param massage and format
     *
     * @param format
     * @param arguments
     */
    public void debug(String format, Object... arguments) {
        logger.trace(format, arguments);
    }

    /**
     * This method for warning with param massage format with exception throwable
     *
     * @param format
     * @param throwable
     */
    public void debug(String format, Throwable throwable) {
        logger.debug(format, throwable);
    }

    /**
     * This method for warning with param massage
     *
     * @param msg
     */
    public void info(String msg) {
        logger.info(msg);
    }

    /**
     * This method for warning with param massage and format
     *
     * @param format
     * @param arguments
     */
    public void info(String format, Object... arguments) {
        logger.info(format, arguments);
    }

    /**
     * This method for warning with param massage format with exception throwable
     *
     * @param format
     * @param throwable
     */
    public void info(String format, Throwable throwable) {
        logger.info(format, throwable);
    }

    /**
     * This method for warning with param massage
     *
     * @param msg
     */
    public void warn(String msg) {
        logger.warn(msg);
    }

    /**
     * This method for warning with param massage and format
     *
     * @param format
     * @param arguments
     */
    public void warn(String format, Object... arguments) {
        logger.warn(format, arguments);
    }

    /**
     * This method for warning with param massage format with exception throwable
     *
     * @param format
     * @param throwable
     */
    public void warn(String format, Throwable throwable) {
        logger.warn(format, throwable);
    }

    /**
     * This method for error with param massage
     *
     * @param msg
     */
    public void error(String msg) {
        logger.error(msg);
    }

    /**
     * This method for error with param massage and format
     *
     * @param format
     * @param arguments
     */
    public void error(String format, Object... arguments) {
        logger.error(format, arguments);
    }

    /**
     * This method for error with param massage format with exception throwable
     *
     * @param format
     * @param throwable
     */
    public void error(String format, Throwable throwable) {
        logger.error(format, throwable);
    }

}
