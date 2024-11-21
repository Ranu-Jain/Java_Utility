package com.sbi.epay.authentication.service;

import com.sbi.epay.authentication.model.AccessTokenRequest;
import com.sbi.epay.authentication.model.PaymentTokenRequest;
import com.sbi.epay.authentication.model.TransactionTokenRequest;
import com.sbi.epay.authentication.model.UserTokenRequest;
import com.sbi.epay.authentication.validator.AuthRequestValidator;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Class Name: AuthServiceImpl
 * *
 * Description: This class implements AuthenticationService interface and it's methods for implementing services.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final LoggerUtility logger = LoggerFactoryUtility.getLogger(AuthenticationService.class);
    private final JwtService jwtService;

    /**
     * generateToken() generates token
     *
     * @param accessTokenRequest Authentication
     * @return JWT token in string form.
     */
    public String generateAccessToken(AccessTokenRequest accessTokenRequest) {
        logger.debug("Initiate generateAccessToken for {}", accessTokenRequest);
        AuthRequestValidator.validAuthRequest(accessTokenRequest);
        return jwtService.generateAccessToken(accessTokenRequest);
    }

    public String generateTransactionToken(TransactionTokenRequest transactionTokenRequest) {
        logger.debug("Initiate generateTransactionToken for {}", transactionTokenRequest);
        AuthRequestValidator.validAuthRequest(transactionTokenRequest);
        return jwtService.generateTransactionToken(transactionTokenRequest);
    }

    public String generateUserToken(UserTokenRequest userTokenRequest) {
        logger.debug("Initiate generateUserToken for {}", userTokenRequest);
        AuthRequestValidator.validAuthRequest(userTokenRequest);
        return jwtService.generateUserLoginToken(userTokenRequest);
    }

    public String generatePaymentToken(PaymentTokenRequest paymentTokenRequest) {
        logger.debug("Initiate generatePaymentToken for {}", paymentTokenRequest);
        AuthRequestValidator.validAuthRequest(paymentTokenRequest);
        return jwtService.generatePaymentToken(paymentTokenRequest);
    }

}
