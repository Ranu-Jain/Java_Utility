package com.sbi.epay.authentication.model;

import lombok.Data;

/**
 * Class Name: UserInfo
 * *
 * Description: Represents a user in the authentication system.
 * This class is mapped to a database entity and contains information about the user, including their ID, name, email, roles, and password.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Data
public class UserInfo {


    /**
     * The name of the user.
     */
    private String username;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The roles assigned to the user, represented as a string.
     */
    private String role;

    /**
     * The password for the user account, stored securely.
     */
    private String password;
}
