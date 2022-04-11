package com.itstore.repository.custom;

import com.itstore.security.identity.IdentityDetailsDTO;

import java.util.Optional;

public interface SecurityIdentityCustom {

    Optional<IdentityDetailsDTO> findIdentity(String username);

}
