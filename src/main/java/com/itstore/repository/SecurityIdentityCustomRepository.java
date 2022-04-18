package com.itstore.repository;

import com.itstore.security.identity.IdentityDetailsDTO;

import java.util.Optional;

public interface SecurityIdentityCustomRepository {

    Optional<IdentityDetailsDTO> findIdentity(String username);

}
