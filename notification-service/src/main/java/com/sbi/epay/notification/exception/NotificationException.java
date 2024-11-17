package com.sbi.epay.notification.exception;

import com.sbi.epay.notification.model.ErrorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@NoArgsConstructor
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
}
