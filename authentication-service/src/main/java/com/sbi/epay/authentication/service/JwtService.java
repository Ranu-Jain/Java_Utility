package com.sbi.epay.authentication.service;

import java.security.Key;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.sbi.epay.authentication.common.ErrorConstants;
import com.sbi.epay.authentication.enumeration.TokenType;
import com.sbi.epay.authentication.exceptionhandler.AuthenticationException;
import com.sbi.epay.authentication.model.AuthRequest;
import com.sbi.epay.authentication.model.UserInfo;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sbi.epay.authentication.common.AppConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

/**
 * Class Name: JwtService
 * *
 * Description: Service Class for generating and validating JSON Web Tokens (JWT).
 * * This class provides methods to create signed JWTs using various inputs, including username/password, API keys, and hashed values.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Component
@RequiredArgsConstructor
public class JwtService {
    private static final LoggerUtility logger = LoggerFactoryUtility.getLogger(JwtService.class);
    public static final String TOKEN_TYPE = "Token Type";
    public static final String USERNAME = "username";
    public static final String ROLE = "role";
    public static final String TYPE = "tokenType";
    public static final String CUSTOMER_ID = "customerId";
    public static final String MID = "mID";
    private final AppConfig appConfig;
    UserInfo userInfo = new UserInfo();


    /**
     * generates token as per authRequest values.
     *
     * @param authRequest
     * @return JWT token in String form.
     */
    public String generateToken(AuthRequest authRequest) {
        logger.info("ClassName - JwtService,MethodName - generateToken, generates token as per authRequest values started.");
        String res = "";
        switch (authRequest.getTokenType()) {
            case CUSTOMER, ORDER -> res = generateTokenWithApiSecret(authRequest);
            case TRANSACTION -> res = generateTokenWithHash(authRequest);
            case MERCHANT -> res = generateTokenWithUsernamePassword(authRequest);
            default -> {
                logger.error("Error occurred while JWT token generation.");
                throw new AuthenticationException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, TOKEN_TYPE, TOKEN_TYPE, TokenType.values()));
            }
        }
        logger.info("ClassName - JwtService,MethodName - generateToken, generates token as per authRequest values ended.");
        return res;
    }

    /**
     * Generates a JWT token with the specified claims, secret key, and expiration time.
     *
     * @param claims         A map of claims to include in the token.
     * @param secretKey      The secret key used to sign the token.
     * @param expirationTime The expiration time of the token in milliseconds.
     * @return A signed JWT token as a string.
     */
    private String generateToken(Map<String, Object> claims, String secretKey, long expirationTime) {
        logger.info("ClassName - JwtService,MethodName - generateToken,generate a JWT token  with the specified claims, secret key, and expiration time.");
        return Jwts.builder().claims(claims).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + expirationTime)).signWith(SignatureAlgorithm.HS512, secretKey).compact();

    }

    /**
     * Generates a JWT token using a username, expirationTime and role.
     *
     * @param authRequest
     * @returnA signed JWT token as a string.
     */
    private String generateTokenWithUsernamePassword(AuthRequest authRequest) {
        logger.info("ClassName - JwtService,MethodName - generateTokenWithUsernamePassword, Generates a JWT token using a username, expirationTime and role.");
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put(USERNAME, userInfo.getUsername());
            claims.put(ROLE, authRequest.getRole());
            claims.put(TYPE, authRequest.getTokenType());
            claims.put(CUSTOMER_ID, authRequest.getCustomerId());
            return generateToken(claims, appConfig.getSecretKey(), authRequest.getExpirationTime());
        } catch (Exception e) {
            logger.error("Error -> ", e);
            throw new AuthenticationException(ErrorConstants.UNAUTHORIZED_ERROR_CODE, ErrorConstants.UNAUTHORIZED_ERROR_MESSAGE);
        }


    }

    /**
     * Generates a JWT token using an API key , role, expirationTime and secret.
     *
     * @param authRequest
     * @return A signed JWT token as a string.
     */
    private String generateTokenWithApiSecret(AuthRequest authRequest) {
        logger.info("ClassName - JwtService,MethodName - generateTokenWithApiSecret, Generates a JWT token using an API key , role, expirationTime and secret.");
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put(USERNAME, userInfo.getUsername());
            claims.put(ROLE, authRequest.getRole());
            claims.put(TYPE, authRequest.getTokenType());
            return generateToken(claims, appConfig.getSecretKey(), authRequest.getExpirationTime());
        } catch (Exception e) {
            logger.error("Error -> ", e);
            throw new AuthenticationException(ErrorConstants.UNAUTHORIZED_ERROR_CODE, ErrorConstants.UNAUTHORIZED_ERROR_MESSAGE);
        }
    }

    /**
     * Generates a JWT token using a hash of the order reference number, expiration time and merchant ID.
     *
     * @param authRequest
     * @return A signed JWT token as a string.
     */
    private String generateTokenWithHash(AuthRequest authRequest) {
        logger.info("ClassName - JwtService,MethodName - generateTokenWithHash, Generates a JWT token using a hash of the order reference number, expiration time and merchant ID.");
        try {
            Map<String, Object> claims = new HashMap<>();
            if (StringUtils.hasText(authRequest.getSbiOrderReference())) {
                claims.put("orderNumber", authRequest.getSbiOrderReference());
            }
            claims.put(MID, authRequest.getMid());
            claims.put(USERNAME, userInfo.getUsername());
            claims.put(ROLE, authRequest.getRole());
            claims.put(TYPE, authRequest.getTokenType());
            if (StringUtils.hasText(authRequest.getCustomerId())) {
                claims.put(CUSTOMER_ID, authRequest.getCustomerId());
            }
            return generateToken(claims, appConfig.getSecretKey(), authRequest.getExpirationTime());
        } catch (Exception e) {
            logger.error("Error -> ", e);
            throw new AuthenticationException(ErrorConstants.UNAUTHORIZED_ERROR_CODE, ErrorConstants.UNAUTHORIZED_ERROR_MESSAGE);
        }

    }


    /**
     * Get all claims from token. and @Param token
     *
     * @param token as a String
     * @return Claims of the token
     */
    private Claims getAllClaimsFromToken(String token) {
        logger.info("ClassName - JwtService,MethodName - getAllClaimsFromToken, getting all claims from token.");
        return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Get SigningKey
     *
     * @return signed key
     */
    private Key getSignKey() {
        logger.info("ClassName - JwtService,MethodName - getSignKey, getting SigningKey.");
        byte[] keyBytes = Decoders.BASE64.decode(appConfig.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);

    }


    /**
     * Get username from token
     *
     * @param token as a String
     * @return String username from token
     */
    public String getUsernameFromToken(String token) {
        logger.info("ClassName - JwtService,MethodName - getUsernameFromToken, username from token.");
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get(USERNAME) == null ? null : String.valueOf(claims.get(USERNAME));
    }


    /**
     * Get Claims from Token
     *
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return All Claims from the token
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        logger.info("ClassName - JwtService,MethodName - getClaimFromToken, Claims from token.");
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    /**
     * Get expiration time from token
     *
     * @param token is as String
     * @return Boolean true if token is expired otherwise false.
     */
    private Boolean isTokenExpired(String token) {
        logger.info("ClassName - JwtService,MethodName - isTokenExpired, getting expiration time from token.");
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date(30));
    }

    /**
     * Getting expiration time from token.
     *
     * @param token
     * @return Date from token
     */
    private Date getExpirationDateFromToken(String token) {
        logger.info("ClassName - JwtService,MethodName - getExpirationDateFromToken, getting expiration time from token.");
        return (Date) getClaimFromToken(token, Claims::getExpiration);

    }

    /**
     * Validate token using user details and expiration time.
     *
     * @param token
     * @param userDetails
     * @return Boolean if username and expiration is valid then return true else return false.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        logger.info("ClassName - JwtService,MethodName - validateToken, Validate token using user details and expiration time.");
        try {
            final String username = getUsernameFromToken(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

        } catch (Exception e) {
            logger.error("Error -> ", e);
            throw new AuthenticationException(ErrorConstants.UNAUTHORIZED_ERROR_CODE, ErrorConstants.UNAUTHORIZED_ERROR_MESSAGE);
        }
    }

    /**
     * TODO: This is created for future purponse for getting API Key from token.
     * <p>
     * Get apiKey from JWT token
     *
     * @param token
     * @return String API Key from token
     */
    private String getApiKeyFromToken(String token) {
        logger.info("ClassName - JwtService,MethodName - getApiKeyFromToken, getting apiKey from JWT token.");
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get("apiKey") == null ? null : String.valueOf(claims.get("apiKey"));
    }

    /**
     * TODO: This is created for future purponse for getting Role from token.
     * <p>
     * Get role from JWT token
     *
     * @param token
     * @return String Role from token
     */
    private String getRoleFromToken(String token) {
        logger.info("ClassName - JwtService,MethodName - getApiKeyFromToken, getting role from JWT token.");
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get(ROLE) == null ? null : String.valueOf(claims.get(ROLE));
    }


}