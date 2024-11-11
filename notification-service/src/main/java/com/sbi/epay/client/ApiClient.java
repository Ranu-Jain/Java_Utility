package com.sbi.epay.client;

import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Class Name: ApiClient
 * *
 * Description:ApiClient Class handles prepareHttpHeaders and prepare HTTPEntity
 * *
 * Author: V1017903(bhushan wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Service
public class ApiClient {
    static LoggerUtility logger = LoggerFactoryUtility.getLogger(ApiClient.class);
    private final String baseUrl;
    private final RestTemplate restTemplate;

    /**
     * This constructor will be used for baseUrl and restTemplate
     *
     * @param baseUrl
     */
    public ApiClient(@Value("${sms.gateway.url}") String baseUrl) {
        this.baseUrl = baseUrl;
        restTemplate = new RestTemplate();
    }

    /**
     * This method will be used for prepare HTTP Header
     *
     * @return header
     */
    public static HttpHeaders prepareHttpHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        return headers;

    }

    /**
     * This method will be used for prepare HttpEntity
     *
     * @param requestBody
     * @param headers
     * @return httpentity
     */
    public static HttpEntity<String> prepareHttpEntity(String requestBody, HttpHeaders headers) {
        return new HttpEntity<>(requestBody, prepareHttpHeaders());
    }

    /**
     * This method will be used for return baseUrl
     *
     * @return baseurl
     */
    protected String getBaseUrl() {
        return baseUrl;
    }

    /**
     * This method will be used for return restTemplate
     *
     * @return restTemplate
     */
    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

}
