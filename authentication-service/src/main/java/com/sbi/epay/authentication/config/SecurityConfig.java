package com.sbi.epay.authentication.config;

import com.sbi.epay.authentication.common.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sbi.epay.authentication.filter.JwtFilter;
import com.sbi.epay.authentication.service.UserInfoServiceImpl;
import org.springframework.security.web.session.SessionManagementFilter;

/**
 * Class Name: SecurityConfig
 * *
 * Description: Security configuration class for the application.
 * This class configures security settings including authentication, authorization, and session management.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CorsConfig corsConfig;
    private final AppConfig appConfig;

    /**
     * Defines a UserDetailsService bean for retrieving user-related data.
     *
     * @return A UserDetailsService implementation.
     */
    @Bean
    UserDetailsService userDetailsService() {
        return new UserInfoServiceImpl();
    }

    /**
     * Configures the security filter chain for the application.
     *
     * @param httpSecurity The HttpSecurity object to customize.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(
                auth -> auth.requestMatchers(appConfig.getWhiteListUrls()).permitAll().anyRequest().authenticated());
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(corsConfig, SessionManagementFilter.class);

        return httpSecurity.build();
    }

    /**
     * Provides an AuthenticationProvider bean for authentication.
     *
     * @return The configured DaoAuthenticationProvider.
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Defines a PasswordEncoder bean for encoding passwords.
     *
     * @return A PasswordEncoder implementation.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * Configures an AuthenticationManager bean for managing authentication.
     *
     * @param config The AuthenticationConfiguration to use.
     * @return The configured AuthenticationManager.
     * @throws Exception if an error occurs during creation.
     */
    @Bean
    AuthenticationManager AuthenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
