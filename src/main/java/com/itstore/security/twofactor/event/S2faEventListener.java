package com.itstore.security.twofactor.event;

import com.itstore.repository.SecurityIdentityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class S2faEventListener {

    public static final Logger LOG = LoggerFactory.getLogger(S2faEventListener.class);

    private final SecurityIdentityRepository repository;

    @Transactional
    @EventListener(S2faSecretGenerated.class)
    public void onS2faSecretGenerated(S2faSecretGenerated event) {
        LOG.debug("Secret 2fa code: {} generated for user : {}", event.getSource(), event.getUserId());
        repository.setSecret(event.getUserId(), (String) event.getSource());
    }

    @Transactional
    @EventListener(S2faSecretAccepted.class)
    public void onS2faSecretAccepted(S2faSecretAccepted event) {
        LOG.debug("Secret 2fa code accepted for user : {}", event.getSource());
        repository.setConfigured2fa((Long) event.getSource(), true);

    }

}
