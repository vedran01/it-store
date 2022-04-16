package com.itstore.security.token;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class JWTAuthenticationToken extends AbstractAuthenticationToken {

    private final TokenClaims claims;

    public JWTAuthenticationToken(TokenClaims claims) {
        this(claims, null);

    }
    public JWTAuthenticationToken(TokenClaims claims, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.claims = claims;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return claims.sub;
    }

    public String getPrincipalUID() {
        return claims.subId;
    }

    public TokenClaims getClaims() {
        return claims;
    }
}
