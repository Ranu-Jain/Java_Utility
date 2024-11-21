package com.sbi.epay.authentication.util.enums;


/**
 * Class Name: TokenType
 * *
 * Description: TokenType is an enumeration that defines the types of tokens used in the application.
 * It helps categorize tokens based on their intended use.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

public enum TokenType {
    /**
     * Represents a token used for access-related operations.
     */
    ACCESS,

    /**
     * Represents a token used for transaction-related operations.
     */
    TRANSACTION,

    /**
     * Represents a token used for payment-related operations.
     */
    PAYMENT,

    /**
     * Represents a token used for user based operation like merchant, admin.
     */
    USER;
}
