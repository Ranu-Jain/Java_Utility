package com.sbi.epay.authentication.service;

import com.sbi.epay.authentication.model.AuthRequest;
import com.sbi.epay.authentication.validator.AuthRequestValidator;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Class Name: AuthServiceImpl
 * *
 * Description: This class implements AuthService interface and it's methods for implementing services.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final LoggerUtility loggerUtility = LoggerFactoryUtility.getLogger(AuthServiceImpl.class);
    private final JwtService jwtService;

    /**
     * generateToken() generates token
     *
     * @param authRequest calls variables as per requirement of CUSTOMER,ORDER,MERCHANT and TRANSACTION.
     * @return JWT token in string form.
     */
    @Override
    public String generateToken(AuthRequest authRequest) {
        loggerUtility.info("ClassName - AuthServiceImpl,MethodName - generateToken, called to generate token as per requirement of CUSTOMER,ORDER,MERCHANT and TRANSACTION.");
        AuthRequestValidator.validAuthRequest(authRequest);
        return jwtService.generateToken(authRequest);
    }
}
