package com.sbi.epay.authentication.model;

import com.sbi.epay.authentication.util.enums.TokenType;
import lombok.Data;

import java.util.List;

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
public class TokenRequest {

    /**
     * JWT request passing tokenType variable as numeration to generate JWT token.
     */
    private TokenType tokenType;

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
    private List<String> roles;

    /**
     * JWT request passing mid variable as string  to generate JWT token.
     */
    private String mid;

    /**
     * JWT request passing expirationTime variable as long/numbers in milliseconds  to generate JWT token.
     */
    private int expirationTime;
}
