package com.sbi.epay.model;


import lombok.AllArgsConstructor;

/**
 * Class Name: ErrorDto
 * *
 * Description:ErrorDto mainly it will be used write error with errorcode and errormessage
 * *
 * Author: V1017903 (bhushan wadekar)
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
