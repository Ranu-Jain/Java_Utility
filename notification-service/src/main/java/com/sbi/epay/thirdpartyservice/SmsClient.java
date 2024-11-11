package com.sbi.epay.thirdpartyservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.client.ApiClient;
import com.sbi.epay.exception.NotificationException;
import com.sbi.epay.model.SmsDTO;
import com.sbi.epay.util.NotificationConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Class Name: SmsClient
 * *
 * Description:smsClient extends ApiClient it holds object mapper and have constructor this class have sendSMS functionality which take smsDTO as Parameter
 * *
 * Author: V1017903(bhushan wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Service
public class SmsClient extends ApiClient {

    private final ObjectMapper objectMapper;
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * This constructor used for baseUrl and objectMapper
     *
     * @param baseUrl for sms
     */
    public SmsClient(@Value("${sms.gateway.url}") String baseUrl) {
        super(baseUrl);
        this.objectMapper = new ObjectMapper();
    }


    /**
     * This method will be used for sending sms
     *
     * @param smsDTO for sending sms
     * @return boolean true or false
     * @throws NotificationException   if any exception occurs
     * @throws JsonProcessingException if any exception occurs
     */
    public boolean sendSMS(SmsDTO smsDTO) throws NotificationException, JsonProcessingException {
        logger.info("ClassName - SmsClient,MethodName - sendSMS,Method-start");

        String smsRequest = objectMapper.writeValueAsString(smsDTO);
        HttpEntity<String> requestEntity = prepareHttpEntity(smsRequest, prepareHttpHeaders());
        ResponseEntity<String> response = getRestTemplate().exchange(getBaseUrl(), HttpMethod.POST, requestEntity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return true;
        } else {
            throw new NotificationException(NotificationConstant.SMS_CODE_005, NotificationConstant.SMS_MSG_005);
        }

    }
}
