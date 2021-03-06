package com.itstore.repository;

import com.itstore.security.model.SecurityIdentity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SecurityIdentityRepository extends CrudRepository<SecurityIdentity, Long>, SecurityIdentityCustomRepository {

    @Modifying
    @Query("UPDATE SecurityIdentity s SET s.secret2fa = :secret WHERE s.id = :id")
    void setSecret(@Param("id") Long id, @Param("secret") String secret);

    @Modifying
    @Query("UPDATE SecurityIdentity s SET s.configured2fa = :configured WHERE s.id = :id")
    void setConfigured2fa(@Param("id") Long id, @Param("configured") boolean configured);

}
