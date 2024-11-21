package com.sbi.epay.authentication.filter;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import com.sbi.epay.authentication.common.ErrorConstants;
import com.sbi.epay.authentication.exception.AuthenticationException;
import com.sbi.epay.authentication.model.EPayPrincipal;
import com.sbi.epay.authentication.model.JwtAuthenticationToken;
import com.sbi.epay.authentication.service.AuthenticationUserService;
import com.sbi.epay.authentication.service.JwtAuthenticationProvider;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sbi.epay.authentication.service.JwtService;


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
    private final AuthenticationUserService authenticationUserService;

    private final String AUTHORIZATION = "Authorization";
    private final String BEARER = "Bearer ";
    private List<String> whitelistURLs;
    @Value("${whitelisted.endpoints}")
    public void setWhitelistURLs( List<String> whitelistURLs) {
        this.whitelistURLs = whitelistURLs;
    }
    /**
     * This method filters incoming requests to extract and validate the JWT token.
     * If the token is valid, it sets the authentication in the security context.
     *
     * @param request     The incoming HTTP request.
     * @param response    The outgoing HTTP response.
     * @param filterChain The filter chain to continue processing.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {
        try {
            String correlationId = request.getHeader("X-Correlation-ID");
            if (StringUtils.isEmpty(correlationId)) {
                correlationId = UUID.randomUUID().toString();
            }
            LoggerFactoryUtility.putMDC("requestId", correlationId);

            final String authHeader = request.getHeader("Authorization");
            String requestUri = request.getRequestURI();

            if (whiteListingCheck(request, response, filterChain, authHeader, requestUri)) return;

            mandatoryCheck(authHeader, requestUri);

            if (loginAPICall(request, response, filterChain, authHeader)) return;

            authentication(request, authHeader);

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            LoggerFactoryUtility.clearMDCContext();
        }
    }

    private void authentication(HttpServletRequest request, String authHeader) throws ParseException {
        final String jwt = authHeader.substring(7);
        final String userName = jwtService.getUsernameFromToken(jwt);
        if (StringUtils.isNotEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {
            EPayPrincipal authenticateUser = authenticationUserService.loadUserByUserName(userName)
                    .orElseThrow(() -> new AuthenticationException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "UserName")));
            if ( jwtService.isTokenValid(jwt, authenticateUser)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jwt);
                jwtAuthenticationToken.setAuthenticated(true);
                JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtService);
                Authentication authenticate = jwtAuthenticationProvider.authenticate(jwtAuthenticationToken);
                context.setAuthentication(authenticate);
                SecurityContextHolder.setContext(context);
            }
        }
    }

    private static boolean loginAPICall(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String authHeader) throws IOException, ServletException {
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }

    private static void mandatoryCheck(String authHeader, String requestUri) {
        if ((StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer "))
                && (!StringUtils.endsWith(requestUri, "/token") || !StringUtils.endsWith(requestUri, "/login"))){
            throw new JwtException("Token is required.");
        }
    }

    private boolean whiteListingCheck(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String authHeader, String requestUri) throws IOException, ServletException {
        if ((StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer "))){
            String withoutContext = requestUri.replaceFirst(request.getContextPath(),"");
            for(String whitelistURL : whitelistURLs){
                if(withoutContext.startsWith(whitelistURL)){
                    filterChain.doFilter(request, response);
                    return true;
                }
            }
        }
        if ((StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) && request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }

}
