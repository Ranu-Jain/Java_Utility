package com.sbi.epay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Name: SmsDTO
 * *
 * Description:this class have two parameters mobileNumber and message this class will be used for sending SMS
 * *
 * Author: V1017903 (bhushan wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SmsDTO {
    private String mobileNumber;
    private String message;
}
