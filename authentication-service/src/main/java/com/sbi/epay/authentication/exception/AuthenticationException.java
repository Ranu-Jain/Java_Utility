package com.sbi.epay.authentication.exception;

import lombok.*;
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

@Getter
public class AuthenticationException extends RuntimeException {

    private String errorCode;
    private String errorMessage;
    private List<ErrorDto> errorMessages;

    public AuthenticationException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public AuthenticationException(List<ErrorDto> errorMessages) {
        this.errorMessages = errorMessages;
    }
}

