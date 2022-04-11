package com.itstore.repository;

import com.itstore.security.model.SecurityPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<SecurityPermission, Long> {
}
