package com.sbi.epay.authentication.enumeration;


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
     * Represents a token used for order-related operations.
     */
    ORDER,

    /**
     * Represents a token used for transaction-related operations.
     */
    TRANSACTION,

    /**
     * Represents a token used for customer-related operations.
     */
    CUSTOMER,

    /**
     * Represents a token used for merchant-related operation.
     */
    MERCHANT;
}
