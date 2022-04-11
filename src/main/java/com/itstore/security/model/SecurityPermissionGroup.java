package com.itstore.security.model;

import com.itstore.core.data.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "sec_permission_group")
public class SecurityPermissionGroup extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "permission_id")
    private SecurityPermission permission;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private SecurityGroup group;

    private boolean permitted;

    public SecurityPermission getPermission() {
        return permission;
    }

    public void setPermission(SecurityPermission permission) {
        this.permission = permission;
    }

    public SecurityGroup getGroup() {
        return group;
    }

    public void setGroup(SecurityGroup group) {
        this.group = group;
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
        SecurityPermissionGroup that = (SecurityPermissionGroup) o;
        return Objects.equals(permission, that.permission) && Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permission, group);
    }
}
