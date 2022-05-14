package com.itstore.security.token;

import com.itstore.security.identity.IdentityDetails;

import java.util.Date;

public class TokenClaims {

   protected String sub;
    protected String subId;

    protected Long userId;
    protected Date iat;
    protected Date nbf;
    protected Date exp;

   public static TokenClaims of(IdentityDetails details) {
        TokenClaims claims = new TokenClaims();
        claims.sub = details.getUsername();
        claims.subId = details.identity().getUuid();
        claims.iat = new Date();
        claims.nbf = Date.from(details.identity().getValidFrom().toInstant());
        claims.exp = details.identity().getValidTill();
        claims.userId = details.identity().getId();
        return claims;
    }

    public Long getUserId() {
        return userId;
    }
}
