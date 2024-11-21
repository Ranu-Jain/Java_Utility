package com.sbi.epay.authentication.common;

/**
 * Class Name: ErrorConstants
 * *
 * Description: This class contains Constant Error Code and Error Messages.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

public final class ErrorConstants {
    public static final String MANDATORY_ERROR_CODE = "1001";
    public static final String MANDATORY_ERROR_MESSAGE = "{0} is mandatory.";
    public static final String INVALID_ERROR_CODE = "1002";
    public static final String INVALID_ERROR_MESSAGE = "{0} is invalid.";
    public static final String NOT_FOUND_ERROR_CODE = "1003";
    public static final String NOT_FOUND_ERROR_MESSAGE = "{0} is not found. Please provide the correct User information.";
    private ErrorConstants() {
    }
}
