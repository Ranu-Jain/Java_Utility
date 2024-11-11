package com.sbi.epay.authentication.config;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Class Name: CorsConfig
 * *
 * Description: CORS (Cross-Origin Resource Sharing) configuration filter.
 * This filter is responsible for handling CORS requests and  providing the necessary headers for cross-origin requests.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Component
public class CorsConfig implements Filter {

    /**
     * Performs filtering on the incoming requests and outgoing responses
     * to add the necessary CORS headers.
     *
     * @param req   The incoming servlet request.
     * @param res   The outgoing servlet response.
     * @param chain The filter chain to pass control to the next filter.
     * @throws IOException      If an input or output error occurs during the filter operation.
     * @throws ServletException If the request for the GET could not be handled.
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-ID, Content-Type, Accept");

        chain.doFilter(request, response);
    }

}
