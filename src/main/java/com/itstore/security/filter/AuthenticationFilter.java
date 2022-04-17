package com.itstore.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itstore.security.identity.IdentityDetails;
import com.itstore.security.token.JWTTokenService;
import com.itstore.security.token.TokenClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

    record AuthenticationRequest(String username, String password){}

    record AuthenticationResponse(String token) {}

    private final AuthenticationManager manager;
    private final JWTTokenService tokenService;
    private final ObjectMapper mapper = new ObjectMapper();

    public AuthenticationFilter(AuthenticationManager manager, JWTTokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        AuthenticationRequest credentials = obtainCredentials(request);

        LOG.debug("Authentication attempt : {} {}", credentials.username, LocalDateTime.now());

        return manager.authenticate(new UsernamePasswordAuthenticationToken(credentials.username, credentials.password));
    }


    private AuthenticationRequest obtainCredentials(HttpServletRequest request) {

        try (InputStream is = request.getInputStream()) {

            return mapper.reader().readValue(is, AuthenticationRequest.class);

        } catch (IOException e) {

            LOG.error("Invalid authentication request: {}", e.getMessage());

            return new AuthenticationRequest(null, null);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        try {

            IdentityDetails details = (IdentityDetails) authResult.getPrincipal();

            TokenClaims claims = TokenClaims.of(details);

            String token = tokenService.generateToken(claims);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            response.getWriter().write(mapper.writeValueAsString(new AuthenticationResponse(token)));

        } catch (Exception e) {

            LOG.error("Error occurred while generating token: {}", e.getMessage());

            chain.doFilter(request, response);
        }
    }
}
