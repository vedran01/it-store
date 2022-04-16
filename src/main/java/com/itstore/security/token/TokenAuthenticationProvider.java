package com.itstore.security.token;

import com.itstore.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(TokenAuthenticationProvider.class);

    private final SecurityService service;

    public TokenAuthenticationProvider(SecurityService service) {
        this.service = service;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LOG.info("Token auth provider");

        JWTAuthenticationToken token = (JWTAuthenticationToken) authentication;

        String uuID = token.getPrincipalUID();

        List<? extends GrantedAuthority> permissions = service.getPermissions(uuID)
                .stream()
                .flatMap(p -> Arrays.stream(p.getPermissions()))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());


        JWTAuthenticationToken auth = new JWTAuthenticationToken(token.getClaims(), permissions);
        auth.setAuthenticated(true);

        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JWTAuthenticationToken.class);
    }
}
