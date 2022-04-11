package com.itstore.security.model;

import com.itstore.core.data.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "sec_permission_identity")
public class SecurityPermissionIdentity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "permission_id")
    private SecurityPermission permission;

    @ManyToOne
    @JoinColumn(name = "identity_id")
    private SecurityIdentity identity;

    boolean permitted;

    public SecurityPermission getPermission() {
        return permission;
    }

    public void setPermission(SecurityPermission permission) {
        this.permission = permission;
    }

    public SecurityIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(SecurityIdentity identity) {
        this.identity = identity;
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
        SecurityPermissionIdentity that = (SecurityPermissionIdentity) o;
        return Objects.equals(identity, that.identity) && Objects.equals(permission, that.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identity, permission);
    }
}
