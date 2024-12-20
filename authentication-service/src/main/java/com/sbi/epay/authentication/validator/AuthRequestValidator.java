package com.sbi.epay.authentication.validator;

import com.sbi.epay.authentication.common.ErrorConstants;
import com.sbi.epay.authentication.dto.ErrorDto;
import com.sbi.epay.authentication.exception.AuthenticationException;
import com.sbi.epay.authentication.model.*;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Class Name: AuthRequestValidator
 * *
 * Description: This class contains authentication request validator and checks null/empty of request variable.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
public class AuthRequestValidator {
    public static final String TOKEN_TYPE = "Token Type";
    public static final String EXPIRY_TIME = "Expiry Time";
    private static final LoggerUtility logger = LoggerFactoryUtility.getLogger(AuthRequestValidator.class);

    /**
     * Validate authentication respective request object based on token type.
     *
     * @param tokenRequest gets request for validation.
     */
    public static void validAuthRequest(TokenRequest tokenRequest) {
        List<ErrorDto> errorDtoList = new ArrayList<>();
        switch (tokenRequest.getTokenType()) {
            case ACCESS -> validAccessTokenRequest((AccessTokenRequest) tokenRequest, errorDtoList);
            case PAYMENT -> validPaymentTokenRequest((PaymentTokenRequest) tokenRequest, errorDtoList);
            case TRANSACTION -> validTransactionTokenRequest((TransactionTokenRequest) tokenRequest, errorDtoList);
            case USER -> validUserTokenRequest((UserTokenRequest) tokenRequest, errorDtoList);
            default ->
                    errorDtoList.add(new ErrorDto(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, TOKEN_TYPE)));
        }

        if (!CollectionUtils.isEmpty(errorDtoList)) {
            logger.error("Error -> ", errorDtoList);
            throw new AuthenticationException(errorDtoList);
        }
    }

    /**
     * Validate Customer authentication request object.
     *
     * @param tokenRequest gets request related to Customer.
     * @param errorDtoList handles error/errorList while validations.
     */
    private static void validAccessTokenRequest(AccessTokenRequest tokenRequest, List<ErrorDto> errorDtoList) {
        if (StringUtils.isEmpty(tokenRequest.getApiKey())) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, "apiKey")));
        } else if (StringUtils.isEmpty(tokenRequest.getSecretKey())) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, "secretKey")));
        } else if (tokenRequest.getExpirationTime() <= 0) {
            errorDtoList.add(new ErrorDto(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, EXPIRY_TIME)));
        }
    }

    /**
     * Validate Order authentication request object.
     *
     * @param tokenRequest gets request related to order.
     * @param errorDtoList handles error/errorList while validations.
     */
    private static void validPaymentTokenRequest(PaymentTokenRequest tokenRequest, List<ErrorDto> errorDtoList) {
        if (StringUtils.isEmpty(tokenRequest.getSbiOrderReferenceNumber())) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, "orderNumber")));
        } else if (StringUtils.isEmpty(tokenRequest.getAtrnNumber())) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, "atrnNumber")));
        } else if (StringUtils.isEmpty(tokenRequest.getMid())) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, "merchant Id")));
        } else if (tokenRequest.getExpirationTime() <= 0) {
            errorDtoList.add(new ErrorDto(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, EXPIRY_TIME)));
        }
    }

    /**
     * Validate Transaction authentication request object.
     *
     * @param tokenRequest gets request related to transaction.
     * @param errorDtoList handles error/errorList while validations.
     */
    private static void validTransactionTokenRequest(TransactionTokenRequest tokenRequest, List<ErrorDto> errorDtoList) {
        if (StringUtils.isEmpty(tokenRequest.getSbiOrderReferenceNumber())) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, "orderNumber")));
        } else if (StringUtils.isEmpty(tokenRequest.getMid())) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, "merchant Id")));
        } else if (tokenRequest.getExpirationTime() <= 0) {
            errorDtoList.add(new ErrorDto(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, EXPIRY_TIME)));
        }
    }

    /**
     * Validate Merchant authentication request object.
     *
     * @param userTokenRequest gets request related to merchant.
     * @param errorDtoList         handles error/errorList while validations.
     */
    private static void validUserTokenRequest(UserTokenRequest userTokenRequest, List<ErrorDto> errorDtoList) {
        if (StringUtils.isEmpty(userTokenRequest.getUsername())) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, "loginName")));
        } else if (StringUtils.isEmpty(userTokenRequest.getPassword())) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, "loginPassword")));
        } else if (userTokenRequest.getExpirationTime() <= 0) {
            errorDtoList.add(new ErrorDto(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, EXPIRY_TIME)));
        }
    }

}