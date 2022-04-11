package com.itstore.security.model;

import com.itstore.core.data.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "sec_permission_role")
public class SecurityPermissionRole extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "permission_id")
    private SecurityPermission permission;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private SecurityRole role;

    private boolean permitted;

    public SecurityPermission getPermission() {
        return permission;
    }

    public void setPermission(SecurityPermission permission) {
        this.permission = permission;
    }

    public SecurityRole getRole() {
        return role;
    }

    public void setRole(SecurityRole role) {
        this.role = role;
    }

    public boolean isPermitted() {
        return permitted;
    }

    public void setPermitted(boolean permitted) {
        this.permitted = permitted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityPermissionRole that = (SecurityPermissionRole) o;
        return Objects.equals(role, that.role) && Objects.equals(permission, that.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, permission);
    }
}
