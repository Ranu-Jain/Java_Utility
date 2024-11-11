package com.sbi.epay.authentication.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * Class Name: UserInfoDetails
 * *
 * Description: This Class contains User details and sends Authentication request to the Parent User Details class.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */


public class UserInfoDetails implements UserDetails {

    String username;
    String password;
    List<GrantedAuthority> authorities;

    /**
     * Set user-information details.
     *
     * @param userInfo gets user-information details.
     */
    public UserInfoDetails(UserInfo userInfo) {
        this.username = userInfo.getUsername();
        this.password = userInfo.getPassword();
        this.authorities = Arrays.stream(userInfo.getRole().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }


    /**
     * @return authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * @return password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * @return username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * @return true if account not expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return true if account not locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return true if credentials not expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * @return true if user is enabled.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }


}
