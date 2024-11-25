package com.sbi.epay.authentication.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccessTokenRequest extends TokenRequest {

    /**
     * JWT request passing apiKey variable as string  to generate JWT token.
     */
    private String apiKey;

    /**
     * JWT request passing secretKey variable as string  to generate JWT token.
     */
    private String secretKey;

    /**
     * JWT request passing customerId variable as string to generate JWT token.
     */
    private String customerId;
}
