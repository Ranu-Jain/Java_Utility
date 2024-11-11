package com.sbi.epay.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sbi.epay.exception.NotificationException;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import com.sbi.epay.model.SmsDTO;
import com.sbi.epay.thirdpartyservice.SmsClient;
import com.sbi.epay.validator.SMSValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Class Name: SmsService
 * *
 * Description:this class Validate the SMs Request by taking SMSDto As Parameter and it has send method which send the sms by taking smsDTO
 * *
 * Author: V1017903 (bhushan wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Service

public final class SmsService {
    LoggerUtility logger = LoggerFactoryUtility.getLogger(SmsService.class);
    private final SmsClient smsClient;

    public SmsService(SmsClient smsClient) {
        this.smsClient = smsClient;
    }

    /**
     * This method will be used for validating smsDTO object and send sms
     *
     * @param smsDTO passing as parameter for send sms
     * @return boolean true or false
     * @throws NotificationException if any exception occur
     */
    public boolean sendSMS(SmsDTO smsDTO) throws JsonProcessingException, NotificationException {
        logger.info("ClassName - SmsService,MethodName -sendSMS, Method-Start");
        SMSValidator.validateSMS(smsDTO);
        logger.info("ClassName - SmsService,MethodName -sendSMS, Method-end");
        return smsClient.sendSMS(smsDTO);
    }

}
