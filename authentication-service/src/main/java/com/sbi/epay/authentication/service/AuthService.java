package com.sbi.epay.authentication.service;

import com.sbi.epay.authentication.model.AuthRequest;

/**
 * Class Name: AuthService
 * *
 * Description: This interface contains generateToken method while generating JWT token
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
public interface AuthService {

    /**
     * generateToken() generates token
     *
     * @param authRequest calls variables as per requirement of CUSTOMER,ORDER,MERCHANT and TRANSACTION.
     * @return JWT token in string form.
     */
    public String generateToken(AuthRequest authRequest);
}
