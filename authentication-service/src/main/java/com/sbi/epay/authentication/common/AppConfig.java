package com.sbi.epay.authentication.common;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class Name: AppConfig
 * *
 * Description:This class contains Resource details which have been accessed @Value annotation.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */


@Data
@Component
@RequiredArgsConstructor
public class AppConfig {

    /**
     * JWT secretKey declaration and fetching secret-key from application.properties files.
     */
    @Value("${jwt.secret.key}")
    private String secretKey;
    /**
     * JWT whiteListUrls declaration  and fetching whiteListUrls from application.properties files.
     */
    @Value("${security.whitelist.url}")
    private String[] whiteListUrls;
}

