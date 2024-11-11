package com.sbi.epay.model;


import com.sbi.epay.util.enums.EmailType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Class Name: EmailDTO
 * *
 * Description:EmailDTO Class have parameters and it will be used for sending mail
 * *
 * Author: V1017903 (bhushan wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    private String recipient;
    private String subject;
    private String from;
    private String cc;
    private String bcc;
    private String body;
    private EmailType emailType;

}