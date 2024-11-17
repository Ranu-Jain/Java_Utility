package com.sbi.epay.encryptdecrypt.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Class Name: EncryptionDecryptionException
 * *
 * Description:This class will be used for handling exceptions
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.1
 */
@Getter
@Setter
public class EncryptionDecryptionException extends RuntimeException {

    private String errorCode;
    private String errorMessage;

    /**
     * This is parametrised constructor for taking errorCode and  errorMessage
     */
    public EncryptionDecryptionException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
