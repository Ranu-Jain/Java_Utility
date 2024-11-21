package com.sbi.epay.authentication.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.text.ParseException;
import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private String jwt;
    private EPayPrincipal principal;
    /**
     * When this constructor is called the JWT has not been validated, however, this class allows
     * an AuthenticationProvider to attempt to authenticate it.
     * @param jwt
     * @throws ParseException
     */
    public JwtAuthenticationToken(String jwt) throws ParseException {
        super(null);  //NO_AUTHORITIES
        this.jwt = jwt;
        setAuthenticated(false);  //NOT_AUTHENTICATED
    }

    public JwtAuthenticationToken(String jwt, EPayPrincipal principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.jwt = jwt;
    }

    @Override
    public String getCredentials() {
        return jwt;
    }

    @Override
    public EPayPrincipal getPrincipal() {
        return principal;
    }

}
