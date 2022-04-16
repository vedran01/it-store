package com.itstore.security.filter;

import com.itstore.security.token.JWTAuthenticationToken;
import com.itstore.security.token.JWTTokenService;
import com.itstore.security.token.TokenClaims;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTHeaderFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JWTHeaderFilter.class);

    private final AuthenticationManager manager;
    private final JWTTokenService tokenService;

    public JWTHeaderFilter(AuthenticationManager manager, JWTTokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        try {

            String token = obtainToken(request);

            LOG.debug("JWT Authorization token: {}", token);

            TokenClaims claims = tokenService.verify(token);

            JWTAuthenticationToken jwtToken = new JWTAuthenticationToken(claims);

            Authentication authentication = manager.authenticate(jwtToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            LOG.error("Error occurred while verifying token: {}", e.getMessage());
        }

        chain.doFilter(request, response);

    }

    public String obtainToken(HttpServletRequest request) {

        final String TOKEN_HEADER = "Authorization";
        final String TOKEN_PREFIX = "Bearer ";

        final String bearer = request.getHeader(TOKEN_HEADER);

        if (StringUtils.isBlank(bearer) || !StringUtils.startsWith(bearer, TOKEN_PREFIX)) {
            LOG.debug("Token has not {} prefix : {}", TOKEN_PREFIX, bearer);
        }

        return StringUtils.replace(bearer, TOKEN_PREFIX, "");
    }
}
