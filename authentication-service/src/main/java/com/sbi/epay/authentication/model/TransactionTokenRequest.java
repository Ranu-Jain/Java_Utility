package com.sbi.epay.authentication.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransactionTokenRequest extends TokenRequest{

    /**
     * JWT request passing sbiOrderReference variable as string  to generate JWT token.
     */
    private String sbiOrderReferenceNumber;

    /**
     * JWT request passing customerId variable as string to generate JWT token.
     */
    private String customerId;

}
