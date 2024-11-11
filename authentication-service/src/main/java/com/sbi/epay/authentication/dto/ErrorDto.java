package com.sbi.epay.authentication.dto;

import lombok.AllArgsConstructor;

/**
 * Class Name: ErrorDto
 * *
 * Description: This class contains error code and error messages passing through constructors.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@AllArgsConstructor
public class ErrorDto {
    private String errorCode;
    private String errorMessage;
}