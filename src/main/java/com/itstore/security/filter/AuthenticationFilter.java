package com.itstore.security.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itstore.security.identity.IdentityDetails;
import com.itstore.security.token.JWTTokenService;
import com.itstore.security.token.TokenClaims;
import com.itstore.security.twofactor.S2faAuthenticator;
import com.itstore.security.twofactor.event.S2faSecretAccepted;
import com.itstore.security.twofactor.event.S2faSecretGenerated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
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

    record AuthenticationRequest(String username, String password) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    record AuthenticationResponse(String token, String qrCode, String message, String status) {
    }

    private final AuthenticationManager manager;
    private final JWTTokenService tokenService;

    private final S2faAuthenticator authenticator;

    private final ApplicationEventPublisher eventPublisher;

    private final ObjectMapper mapper = new ObjectMapper();

    public AuthenticationFilter(AuthenticationManager manager,
                                JWTTokenService tokenService,
                                S2faAuthenticator authenticator,
                                ApplicationEventPublisher eventPublisher) {
        this.manager = manager;
        this.tokenService = tokenService;
        this.authenticator = authenticator;
        this.eventPublisher = eventPublisher;
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


            String code = request.getParameter("code");

            if (details.requires2faCode() || StringUtils.isNotBlank(code)) {

                boolean success = isSuccess(details, code);

                if (success && !details.identity().getConfigured2fa()) {
                    eventPublisher.publishEvent(new S2faSecretAccepted(details.identity().getId()));
                }

                if (!success) {
                    LOG.debug("Current code: {}", authenticator.code(details.secret2fa()));
                    request.setAttribute("status", "CODE_2FA");
                    this.unsuccessfulAuthentication(request, response, new InsufficientAuthenticationException("Additional authentication"));
                    return;
                }
            } else if (details.requires2faSetup()) {

                final String secret2fa = authenticator.generatePassword();
                final String code2fa = authenticator.generateQRCode(details.getUsername(), secret2fa);

                eventPublisher.publishEvent(new S2faSecretGenerated(details.identity().getId(), secret2fa));

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(mapper.writeValueAsString(new AuthenticationResponse(null, code2fa,  null,"SETUP_2FA")));
                return;

            }

            TokenClaims claims = TokenClaims.of(details);

            String token = tokenService.generateToken(claims);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            response.getWriter().write(mapper.writeValueAsString(new AuthenticationResponse(token, null, null, "SUCCESS")));


        } catch (Exception e) {

            LOG.error("Error occurred while generating token: {}", e.getMessage());

            chain.doFilter(request, response);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String status = (String) request.getAttribute("status");
        response.getWriter().write(mapper.writeValueAsString(new AuthenticationResponse(null, null, failed.getMessage(), status)));
    }

    private boolean isSuccess(IdentityDetails details, String code) {

        if (StringUtils.isNotBlank(code) && NumberUtils.isParsable(code)) {
            return authenticator.validate(details.secret2fa(), Integer.parseInt(code));
        }

        return false;
    }
}
