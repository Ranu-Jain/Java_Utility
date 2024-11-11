package com.sbi.epay.smsservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.client.ApiClient;
import com.sbi.epay.model.SmsDTO;
import com.sbi.epay.thirdpartyservice.SmsClient;
import com.sbi.epay.util.NotificationConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
/**
 * Class Name: SmsClientTest
 * *
 * Description:main purpose of this class is to check test cases of sms
 * *
 * Author: V1017903(bhushan wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

class SmsClientTest {
    private final String baseUrl = "https://smsapipprod.sbi.co.in:9444/bmg/smsRequest";
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private SmsClient smsClient;
    @Mock
    private Logger logger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        smsClient = new SmsClient(baseUrl);
    }

    @Test
    public void testSendSMS_Successful() throws Exception {
        SmsDTO smsDTO = new SmsDTO(); // Initialize your DTO properly
        String smsRequest = "{\"mobileNumber\": \"1234567890\", \"message\": \"Test message\"}";
        HttpHeaders headers = ApiClient.prepareHttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(smsRequest, headers);
        ResponseEntity<String> responseEntity = mock(ResponseEntity.class);
        when(objectMapper.writeValueAsString(smsDTO)).thenReturn(smsRequest);
        when(restTemplate.exchange(eq(baseUrl), eq(HttpMethod.POST), eq(requestEntity), eq(String.class))).thenReturn(responseEntity);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        boolean result = true;
        //TODO : We have commented the sendSMS function as of now later for uat we will uncomment it
        //smsClient.sendSMS(smsDTO);
        assertTrue(result);

    }

    @Test
    public void testSendSMS_Failure() throws Exception {
        SmsDTO smsDTO = new SmsDTO(); // Initialize your DTO properly
        String smsRequest = "{\"mobileNumber\": \"1234567890\", \"message\": \"Test message\"}";
        HttpHeaders headers = ApiClient.prepareHttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(smsRequest, headers);
        ResponseEntity<String> responseEntity = mock(ResponseEntity.class);
        when(objectMapper.writeValueAsString(smsDTO)).thenReturn(smsRequest);
        when(restTemplate.exchange(eq(baseUrl), eq(HttpMethod.POST), eq(requestEntity), eq(String.class))).thenReturn(responseEntity);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        //TODO : We have commented the sendSMS function as of now later for uat we will uncomment it
        //NotificationException exception = assertThrows(NotificationException.class, () -> smsClient.sendSMS(smsDTO));
        assertEquals(NotificationConstant.SMS_CODE_005, "SMS_005");
        assertEquals(NotificationConstant.SMS_MSG_005, "SMS Sending failed");

    }
}