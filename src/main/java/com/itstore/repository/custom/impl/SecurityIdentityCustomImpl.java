package com.itstore.repository.custom.impl;

import com.itstore.ItStoreApplication;
import com.itstore.security.identity.IdentityDetailsDTO;
import com.itstore.repository.custom.SecurityIdentityCustom;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

@SuppressWarnings("unused")
public class SecurityIdentityCustomImpl implements SecurityIdentityCustom {

    private final EntityManager em;

    public SecurityIdentityCustomImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<IdentityDetailsDTO> findIdentity(String username) {

        try {
            Query query = em.createNamedQuery("Identity.findByUsername")
                    .setParameter("username", username);
            return Optional.ofNullable((IdentityDetailsDTO) query.getSingleResult());
        }
        catch (NoResultException ex) {
            ItStoreApplication.LOG.warn("{}: username : {} ", ex.getMessage(), username);
            return Optional.empty();
        }
    }
}
