package com.sbi.epay.notification.service;


import com.sbi.epay.notification.model.EmailDTO;
import com.sbi.epay.notification.exception.NotificationException;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import com.sbi.epay.notification.thirdpartyservice.EmailClient;
import com.sbi.epay.notification.validator.EmailValidator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Class Name: EmailService
 * *
 * Description:EmailService Class Mainly used for validating the emailDTO and used for sendingMail on taking emailDTO object
 * *
 * Author: V1017903 (bhushan wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailClient emailClient;
    LoggerUtility logger = LoggerFactoryUtility.getLogger(EmailService.class);

    /**
     * This method that is used for validating emailDTO object and send email
     *
     * @param emailDTO object for validating and sending email
     * @return boolean true or false
     * @throws NotificationException if any exception occurs
     */
    public boolean sendEmail(EmailDTO emailDTO) throws NotificationException {
        logger.info("ClassName - EmailService, MethodName -sendEmail - start");
        EmailValidator.validateEMAIL(emailDTO);
        logger.info("ClassName - EmailService, MethodName -sendEmail - end");
        return emailClient.sendEmail(emailDTO);
    }
}

