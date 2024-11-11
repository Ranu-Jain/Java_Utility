package com.sbi.epay.authentication.filter;

import java.io.IOException;

import com.sbi.epay.authentication.common.ErrorConstants;
import com.sbi.epay.authentication.exceptionhandler.AuthenticationException;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sbi.epay.authentication.service.JwtService;
import com.sbi.epay.authentication.service.UserInfoServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Class Name: JwtFilter
 * *
 * Description: JWT Filter for handling authentication based on JWT tokens. This filter checks for the presence of a JWT in the Authorization header
 * and authenticates the user if the token is valid. JwtFilter is a custom filter that processes incoming HTTP requests to validate JWT tokens.
 * It extends OncePerRequestFilter to ensure it is invoked once per request.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final LoggerUtility loggerUtility = LoggerFactoryUtility.getLogger(JwtFilter.class);
    private final JwtService jwtService;
    private final UserInfoServiceImpl userInfoService;

    private final String AUTHORIZATION = "Authorization";
    private final String BEARER = "Bearer ";

    /**
     * This method filters incoming requests to extract and validate the JWT token.
     * If the token is valid, it sets the authentication in the security context.
     *
     * @param request     The incoming HTTP request.
     * @param response    The outgoing HTTP response.
     * @param filterChain The filter chain to continue processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        loggerUtility.info("ClassName - JwtFilter,MethodName - doFilterInternal, started.");
        String authHeader = request.getHeader(AUTHORIZATION);
        String token = null;
        String username = null;
        try {

            if (authHeader != null && authHeader.startsWith(BEARER)) {
                loggerUtility.info("authHeader started with : {}", authHeader);
                token = authHeader.substring(7);
                username = jwtService.getUsernameFromToken(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userdetails = userInfoService.loadUserByUsername(username);
                if (Boolean.TRUE.equals(jwtService.validateToken(token, userdetails))) {
                    loggerUtility.info("JWT token is validated and returned true.");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userdetails, null, userdetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (ServletException | IOException | RuntimeException e) {
            loggerUtility.error("Error ->  ", e);
            throw new AuthenticationException(ErrorConstants.UNAUTHORIZED_ERROR_CODE, ErrorConstants.UNAUTHORIZED_ERROR_MESSAGE);

        }
        loggerUtility.info("ClassName - JwtFilter,MethodName - doFilterInternal, ended.");
    }

}
