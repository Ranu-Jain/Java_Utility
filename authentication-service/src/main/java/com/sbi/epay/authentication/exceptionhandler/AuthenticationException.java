package com.sbi.epay.authentication.exceptionhandler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.sbi.epay.authentication.dto.ErrorDto;

import java.util.List;

/**
 * Class Name: AuthenticationException
 * *
 * Description: BaseException is a custom exception class that extends RuntimeException.
 * It is used to represent application-specific exceptions with an error code and message.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthenticationException extends RuntimeException {

    /**
     * The code representing the type of error.
     */
    private String errorCode;

    /**
     * A descriptive message providing details about the error.
     */
    private String errorMessage;

    /**
     * A descriptive List of messages providing details about the error.
     */
    private List<ErrorDto> errorMessagesList;

    /**
     * Constructs a new BaseException with the specified error code and error message.
     *
     * @param errorCode    the error code representing the exception type
     * @param errorMessage a detailed message about the exception
     */
    public AuthenticationException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * this is parametrized constructor taking List of ErrorDTO
     *
     * @param errorMessages contains list of errors.
     */
    public AuthenticationException(List<ErrorDto> errorMessages) {
        this.errorMessagesList = errorMessages;
    }

    /**
     * this is  Default constructor used for test cases
     */
    public AuthenticationException() {
        super();
    }
}

