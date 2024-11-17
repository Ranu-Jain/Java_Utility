package com.sbi.epay.notification.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@RequiredArgsConstructor
public class ErrorDto {
    private String errorCode;
    private String errorMessage;
}
