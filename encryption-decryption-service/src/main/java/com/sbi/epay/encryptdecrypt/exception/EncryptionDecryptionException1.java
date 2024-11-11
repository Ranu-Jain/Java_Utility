package com.sbi.epay.encryptdecrypt.exception;


/**
 *
 * Class Name: EncryptionDecryptionException1
 * *
 * Description:This class will be used for handling exceptions
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Deprecated
public class EncryptionDecryptionException1 extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;


    public EncryptionDecryptionException1(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
