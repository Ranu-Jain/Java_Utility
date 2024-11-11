package com.sbi.epay.authentication.service;

import java.util.Optional;

import com.sbi.epay.authentication.model.UserInfoDetails;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sbi.epay.authentication.model.UserInfo;

/**
 * Class Name: UserInfoService
 * *
 * Description: This Class contains User details service.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Service
public class UserInfoServiceImpl implements UserDetailsService {
    private static final LoggerUtility loggerUtility = LoggerFactoryUtility.getLogger(UserInfoServiceImpl.class);

    /**
     * @param username the username identifying the user whose data is required.
     * @return user-details if user found
     * @throws UsernameNotFoundException if username not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        loggerUtility.info("ClassName - UserInfoServiceImpl,MethodName - loadUserByUsername, getting role from JWT token.");
        UserInfo userInfo = new UserInfo();
        Optional<UserInfo> userInfo1 = Optional.of(userInfo);
        return userInfo1.map(UserInfoDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not Found " + username));
    }

}
