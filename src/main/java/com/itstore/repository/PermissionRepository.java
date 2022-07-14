package com.itstore.repository;

import com.itstore.model.security.SecurityPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<SecurityPermission, Long> {
}
