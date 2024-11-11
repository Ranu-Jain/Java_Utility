package com.sbi.epay.exception;

import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import com.sbi.epay.model.ErrorDto;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Class Name: NotificationException
 * *
 * Description:NotificationException Class it have errorCode and errorMessage as parameter
 * *
 * Author: V1017903(bhushan wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@AllArgsConstructor
public class NotificationException extends RuntimeException {

    private String errorCode;
    private String errorMessage;
    private List<ErrorDto> errorMessages;

    /**
     * This is parametrised constructor for taking errorCode and  errorMessage
     *
     * @param errorCode
     * @param errorMessage
     */
    public NotificationException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * This constructor used for list of errorMessages
     *
     * @param errorMessages
     */
    public NotificationException(List<ErrorDto> errorMessages) {
        this.errorMessages = errorMessages;
    }

    /**
     * this is  Default constructor used for test cases
     */
    public NotificationException() {
        super();
    }

}
