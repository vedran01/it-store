package com.itstore.security.twofactor.event;

import org.springframework.context.ApplicationEvent;

public class S2faSecretGenerated extends ApplicationEvent {

    private Long userId;

    public S2faSecretGenerated(Long userId, String secret2fa) {
        super(secret2fa);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
