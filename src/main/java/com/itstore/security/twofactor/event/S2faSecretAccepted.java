package com.itstore.security.twofactor.event;

import org.springframework.context.ApplicationEvent;

public class S2faSecretAccepted extends ApplicationEvent {

    public S2faSecretAccepted(Long userId) {
        super(userId);
    }
}
