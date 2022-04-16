package com.itstore.security.token;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.itstore.core.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTTokenService {

    private static final Logger LOG = LoggerFactory.getLogger(JWTTokenService.class);
    private final Algorithm algorithmHS = Algorithm.HMAC256("secret");


    public String generateToken(TokenClaims claims) {

        try {

            Builder TOKEN_BUILDER = JWT.create();

            Class<? extends TokenClaims> aClass = claims.getClass();

            Map<String, Object> claimsJWT = new HashMap<>();

            for (Field field : aClass.getDeclaredFields()) {
                Object value = field.get(claims);
                if (value != null) {
                    String claim = field.getName();
                    claimsJWT.put(claim, value);
                }
            }

            claimsJWT.computeIfAbsent("exp", s -> Util.nowPlusDuration(Duration.of(15, ChronoUnit.DAYS)));

            TOKEN_BUILDER
                    .withIssuer("it-store")
                    .withPayload(claimsJWT);

            return TOKEN_BUILDER.sign(algorithmHS);

        } catch (JWTCreationException e) {
            LOG.error("Error creating JWT token: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public TokenClaims verify(String token) {

        try {

            JWTVerifier verifier = JWT.require(algorithmHS).build();
            DecodedJWT decoded = verifier.verify(token);

            TokenClaims claims = new TokenClaims();

            Map<String, Claim> decodedClaims = decoded.getClaims();

            Class<? extends TokenClaims> aClass = claims.getClass();

            for (Field field : aClass.getDeclaredFields()) {
                Claim claim = decodedClaims.get(field.getName());
                field.setAccessible(true);
                ReflectionUtils.setField(field, claims, claim.as(field.getType()));
            }

            return claims;

        } catch (JWTVerificationException e) {
            LOG.error("Error occurred while verify token: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
