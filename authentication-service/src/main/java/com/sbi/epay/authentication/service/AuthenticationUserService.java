package com.sbi.epay.authentication.service;

import com.sbi.epay.authentication.model.EPayPrincipal;
import java.util.Optional;

/**
 * Class Name: AuthenticationUserService
 * *
 * Description: This interface contains methods getUserByUserName method .
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
public interface AuthenticationUserService {
    Optional<EPayPrincipal> loadUserByUserName(String userName);
}
