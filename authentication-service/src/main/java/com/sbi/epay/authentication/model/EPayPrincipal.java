package com.sbi.epay.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EPayPrincipal implements UserDetails {

    private String authenticationKey;
    private String mid;
    private String orderRef;
    private String transactionRef;
    private String passCode;
    private String tokenType;
    private String token;
    private List<String> userRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.passCode;
    }

    @Override
    public String getUsername() {
        return this.authenticationKey;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
