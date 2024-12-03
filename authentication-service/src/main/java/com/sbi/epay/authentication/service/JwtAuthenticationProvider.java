package com.sbi.epay.authentication.service;

import com.sbi.epay.authentication.model.EPayPrincipal;
import com.sbi.epay.authentication.model.EPayPlatformJwtClaimsSet;
import com.sbi.epay.authentication.model.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;

    public JwtAuthenticationProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
        Claims claims = jwtService.getAllClaimsFromToken(jwtToken.getCredentials());
        return createSuccessAuthentication(jwtToken.getCredentials(), claims);
    }

    private JwtAuthenticationToken createSuccessAuthentication(String jwt, Claims claims) {
        EPayPrincipal principal = derivePrincipal(jwt, claims);
        JwtAuthenticationToken result = new JwtAuthenticationToken(jwt,
                principal, principal.getAuthorities());
        result.setDetails(claims.toString());
        result.setAuthenticated(true);
        return result;
    }

    private EPayPrincipal derivePrincipal(String jwt, Claims claims) {
        EPayPrincipal authenticateEntity = new EPayPrincipal();
        if(ObjectUtils.isEmpty(claims.get(EPayPlatformJwtClaimsSet.ROLE))) {
            authenticateEntity.setUserRole(List.of("User"));
        } else {
            authenticateEntity.setUserRole(claims.get(EPayPlatformJwtClaimsSet.ROLE, ArrayList.class));
        }
        authenticateEntity.setAuthenticationKey(claims.get(EPayPlatformJwtClaimsSet.USERNAME, String.class));
        authenticateEntity.setMid(claims.get(EPayPlatformJwtClaimsSet.MID, String.class));
        authenticateEntity.setOrderRef(claims.get(EPayPlatformJwtClaimsSet.ORDER_NUMBER, String.class));
        authenticateEntity.setTransactionRef(claims.get(EPayPlatformJwtClaimsSet.ATRN_NUMBER, String.class));
        authenticateEntity.setTokenType(claims.get(EPayPlatformJwtClaimsSet.TYPE, String.class));
        authenticateEntity.setToken(jwt);
        return authenticateEntity;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
