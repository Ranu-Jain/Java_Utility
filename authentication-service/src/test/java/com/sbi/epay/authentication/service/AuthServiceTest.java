package com.sbi.epay.authentication.service;

import com.sbi.epay.authentication.model.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.*;
import org.mockito.Mock;

import com.sbi.epay.authentication.enumeration.TokenType;
import com.sbi.epay.authentication.model.AuthRequest;

/**
 * Class Name: AuthServiceTest
 * *
 * Description: AuthServiceTest class handles testcases related to JWT token generation and validations.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@SpringBootTest
class AuthServiceTest {
    /**
     * Gets secretKey from application.property file.
     */
    @Value("${jwt.secret.key}")
    public String secretKey;

    /**
     * Gets apiKey from application.property file.
     */
    @Value("${jwt.api.key}")
    public String apiKey;


    /**
     * Gets sbiOrderReference from application.property file.
     */
    @Value("${sbiOrderReference.value}")
    public String sbiOrderReference;

    /**
     * Gets mid from application.property file.
     */
    @Value("${mid.value}")
    public String mid;

    /**
     * Gets hashValue from application.property file.
     */
    @Value("${hashValue.value}")
    public String hashValue;

    /**
     * Gets username from application.property file.
     */
    @Value("${spring.security.user.name}")
    public String username;

    /**
     * Gets password from application.property file.
     */
    @Value("${spring.security.user.password}")
    public String password;

    /**
     * Gets role from application.property file.
     */
    @Value("${spring.security.user.roles}")
    public String role;

    /**
     * Gets expirationTime from application.property file.
     */
    @Value("${jwt.expiration.time}")
    public long expirationTime;

    /**
     * AuthServiceImpl class mocked.
     */
    @Mock
    public AuthServiceImpl authService;

    /**
     * Object created for AuthRequest.
     */
    AuthRequest authRequest = new AuthRequest();

    /**
     * calls before each method called.
     */
    @BeforeEach
    void setUp() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setRole(role);
    }

    /**
     * @throws Exception if catches any exception before all.
     */
    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * Generate Token test case for token type CUSTOMER.
     */
    @Test
    void generateTokenForCustomer() {
        authRequest.setTokenType(TokenType.CUSTOMER);
        authRequest.setApiKey(apiKey);
        authRequest.setSecretKey(secretKey);
        authRequest.setExpirationTime(expirationTime);

        assertNull(authService.generateToken(authRequest));
    }

    /**
     * Generate Token test case for token type ORDER.
     */
    @Test
    void generateTokenForOrder() {
        authRequest.setTokenType(TokenType.ORDER);
        authRequest.setApiKey(apiKey);
        authRequest.setSecretKey(secretKey);
        authRequest.setExpirationTime(expirationTime);

        assertNull(authService.generateToken(authRequest));
    }

    /**
     * Generate Token test case for token type TRANSACTION.
     */
    @Test
    void generateTokenForTransaction() {
        authRequest.setTokenType(TokenType.TRANSACTION);
        authRequest.setExpirationTime(expirationTime);
        assertNull(authService.generateToken(authRequest));
    }

    /**
     * Generate Token test case for token type MERCHANT.
     */
    @Test
    void generateTokenForMerchant() {
        authRequest.setTokenType(TokenType.MERCHANT);
        authRequest.setUsername(username);
        authRequest.setPassword(password);
        authRequest.setExpirationTime(expirationTime);

        assertNull(authService.generateToken(authRequest));
    }

    /**
     * tears down after test executes.
     */
    @AfterEach
    void tearDown() {
    }

    /**
     * @throws Exception if catches any exception after execution.
     */
    @AfterAll
    public static void tearDownAfterClass() throws Exception {
    }
}