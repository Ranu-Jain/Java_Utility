package com.sbi.epay.authentication.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentTokenRequest extends TokenRequest {

    /**
     * JWT request passing sbiOrderReference variable as string to generate JWT token.
     */
    private String sbiOrderReferenceNumber;

    /**
     * JWT request passing atrnNumber variable as string to generate JWT token.
     */
    private String atrnNumber;

}
