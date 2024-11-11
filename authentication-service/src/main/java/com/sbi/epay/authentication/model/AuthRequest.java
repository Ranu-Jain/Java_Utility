package com.sbi.epay.authentication.model;

import com.sbi.epay.authentication.enumeration.TokenType;
import lombok.Data;

/**
 * Class Name: AuthRequest
 * *
 * Description: This dto class stores variables relate to  JWT token generation request.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Data
public class AuthRequest {

    /**
     * JWT request passing tokenType variable as numeration to generate JWT token.
     */
    private TokenType tokenType;

    /**
     * JWT request passing customerId variable as string to generate JWT token.
     */
    private String customerId;

    /**
     * JWT request passing username variable as string to generate JWT token.
     */
    private String username;

    /**
     * JWT request passing password variable as string  to generate JWT token.
     */
    private String password;

    /**
     * JWT request passing role variable as string  to generate JWT token.
     */
    private String role;

    /**
     * JWT request passing apiKey variable as string  to generate JWT token.
     */
    private String apiKey;

    /**
     * JWT request passing secretKey variable as string  to generate JWT token.
     */
    private String secretKey;

    /**
     * JWT request passing mid variable as string  to generate JWT token.
     */
    private String mid;

    /**
     * JWT request passing sbiOrderReference variable as string  to generate JWT token.
     */
    private String sbiOrderReference;

    /**
     * JWT request passing hashValue variable as string  to generate JWT token.
     */
    private String hashValue;

    /**
     * JWT request passing expirationTime variable as long/numbers in milliseconds  to generate JWT token.
     */
    private long expirationTime;
}
