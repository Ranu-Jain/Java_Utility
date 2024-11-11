package com.sbi.epay.authentication.validator;

import com.sbi.epay.authentication.common.ErrorConstants;

import com.sbi.epay.authentication.enumeration.TokenType;
import com.sbi.epay.authentication.exceptionhandler.AuthenticationException;
import com.sbi.epay.authentication.model.AuthRequest;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import com.sbi.epay.authentication.dto.ErrorDto;
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
    private static final LoggerUtility logger = LoggerFactoryUtility.getLogger(AuthRequestValidator.class);

    public static final String TOKEN_TYPE = "Token Type";
    public static final String EXPIRY_TIME = "Expiry Time";
    public static final String API_KEY = " API Key";
    public static final String SECRET_KEY = " Secret Key";
    public static final String USER_NAME = "User name";
    public static final String PASSWORD = "Password";


    /**
     * Validate authentication respective request object based on token type.
     *
     * @param authRequest gets request for validation.
     */
    public static void validAuthRequest(AuthRequest authRequest) {
        logger.info("ClassName - AuthRequestValidator,MethodName - validAuthRequest, Validate authentication respective request object based on token types started.");
        List<ErrorDto> errorDtoList = new ArrayList<>();
        switch (authRequest.getTokenType()) {
            case CUSTOMER -> validCustomerAuthRequest(authRequest, errorDtoList);
            case ORDER -> validOrderAuthRequest(authRequest, errorDtoList);
            case TRANSACTION -> validTransactionAuthRequest(authRequest, errorDtoList);
            case MERCHANT -> validMerchantAuthRequest(authRequest, errorDtoList);
            default ->
                    errorDtoList.add(new ErrorDto(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, TOKEN_TYPE, TOKEN_TYPE, TokenType.values())));
        }

        if (!CollectionUtils.isEmpty(errorDtoList)) {
            logger.error("Error -> ", errorDtoList);
            throw new AuthenticationException(errorDtoList);
        }
        logger.info("ClassName - AuthRequestValidator,MethodName - validAuthRequest, Validate authentication respective request object based on token types ended.");
    }

    /**
     * Validate Customer authentication request object.
     *
     * @param authRequest  gets request related to Customer.
     * @param errorDtoList handles error/errorList while validations.
     */
    private static void validCustomerAuthRequest(AuthRequest authRequest, List<ErrorDto> errorDtoList) {
        logger.info("ClassName - AuthRequestValidator,MethodName - validCustomerAuthRequest, Validate Customer authentication request object started.");
        if (StringUtils.isEmpty(authRequest.getApiKey())) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, API_KEY)));
        } else if (authRequest.getSecretKey().isEmpty()) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, SECRET_KEY)));
        } else if (authRequest.getExpirationTime() == 0) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, EXPIRY_TIME)));
        } else if (ObjectUtils.isEmpty(authRequest.getTokenType()) || TokenType.CUSTOMER != authRequest.getTokenType()) {
            errorDtoList.add(new ErrorDto(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, TOKEN_TYPE, TOKEN_TYPE, TokenType.values())));
        }
        logger.info("ClassName - AuthRequestValidator,MethodName - validCustomerAuthRequest, Validate Customer authentication request object ended.");
    }

    /**
     * Validate Order authentication request object.
     *
     * @param authRequest  gets request related to order.
     * @param errorDtoList handles error/errorList while validations.
     */
    private static void validOrderAuthRequest(AuthRequest authRequest, List<ErrorDto> errorDtoList) {
        logger.info("ClassName - AuthRequestValidator,MethodName - validOrderAuthRequest, Validate Order authentication request object started.");
        if (authRequest.getApiKey().isEmpty()) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, API_KEY)));
        } else if (authRequest.getSecretKey().isEmpty()) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, SECRET_KEY)));
        } else if (authRequest.getExpirationTime() == 0) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, EXPIRY_TIME)));
        } else if (authRequest.getTokenType() == null) {
            errorDtoList.add(new ErrorDto(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, TOKEN_TYPE, TOKEN_TYPE, TokenType.values())));
        }
        logger.info("ClassName - AuthRequestValidator,MethodName - validOrderAuthRequest, Validate Order authentication request object ended.");
    }

    /**
     * Validate Transaction authentication request object.
     *
     * @param authRequest  gets request related to transaction.
     * @param errorDtoList handles error/errorList while validations.
     */
    private static void validTransactionAuthRequest(AuthRequest authRequest, List<ErrorDto> errorDtoList) {
        logger.info("ClassName - AuthRequestValidator,MethodName - validTransactionAuthRequest, Validate Transaction authentication request object started.");
        if (authRequest.getExpirationTime() == 0) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, EXPIRY_TIME)));
        } else if (authRequest.getTokenType() == null) {
            errorDtoList.add(new ErrorDto(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, TOKEN_TYPE, TOKEN_TYPE, TokenType.values())));
        }
        logger.info("ClassName - AuthRequestValidator,MethodName - validTransactionAuthRequest, Validate Transaction authentication request object ended.");
    }

    /**
     * Validate Merchant authentication request object.
     *
     * @param authRequest  gets request related to merchant.
     * @param errorDtoList handles error/errorList while validations.
     */
    private static void validMerchantAuthRequest(AuthRequest authRequest, List<ErrorDto> errorDtoList) {
        logger.info("ClassName - AuthRequestValidator,MethodName - validMerchantAuthRequest, Validate Merchant authentication request object started.");
        if (authRequest.getUsername().isEmpty()) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, USER_NAME)));
        } else if (authRequest.getPassword().isEmpty()) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, PASSWORD)));
        } else if (authRequest.getExpirationTime() == 0) {
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, EXPIRY_TIME)));
        } else if (authRequest.getTokenType() == null) {
            errorDtoList.add(new ErrorDto(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, TOKEN_TYPE, TOKEN_TYPE, TokenType.values())));
        }
        logger.info("ClassName - AuthRequestValidator,MethodName - validMerchantAuthRequest, Validate Merchant authentication request object ended.");
    }

}