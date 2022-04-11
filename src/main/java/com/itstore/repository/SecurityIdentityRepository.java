package com.itstore.repository;

import com.itstore.security.model.SecurityIdentity;
import com.itstore.repository.custom.SecurityIdentityCustom;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SecurityIdentityRepository extends CrudRepository<SecurityIdentity, Long>, SecurityIdentityCustom {

    Optional<SecurityIdentity> findByUserUsername(String username);

}
