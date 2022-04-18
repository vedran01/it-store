package com.itstore.repository.impl;

import com.itstore.ItStoreApplication;
import com.itstore.security.identity.IdentityDetailsDTO;
import com.itstore.repository.SecurityIdentityCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

@SuppressWarnings("unused")
public class SecurityIdentityCustomRepositoryImpl implements SecurityIdentityCustomRepository {

    private final EntityManager em;

    public SecurityIdentityCustomRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<IdentityDetailsDTO> findIdentity(String username) {

        try {

            Query query = em.createNamedQuery("Identity.findByUsername").setParameter("username", username);

            return Optional.ofNullable((IdentityDetailsDTO) query.getSingleResult());

        } catch (NoResultException ex) {
            ItStoreApplication.LOG.warn("{}: username : {} ", ex.getMessage(), username);
            return Optional.empty();
        }
    }
}
