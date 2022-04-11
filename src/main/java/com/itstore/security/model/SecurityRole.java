package com.itstore.security.model;

import com.itstore.core.data.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "sec_role")
public class SecurityRole extends AbstractEntity {

    private String name;
    private String key;
    private String uuid;
    private boolean enabled;

    @ManyToMany(mappedBy = "roles")
    private Set<SecurityIdentity> identities = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    private Set<SecurityGroup> groups = new HashSet<>();

    @OneToMany(mappedBy = "role", orphanRemoval = true)
    private Set<SecurityPermissionRole> permissions = new HashSet<>();

    public SecurityRole() {
        uuid = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String alias) {
        this.uuid = alias;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<SecurityIdentity> getIdentities() {
        return identities;
    }

    public void setIdentities(Set<SecurityIdentity> users) {
        this.identities = users;
    }

    public Set<SecurityPermissionRole> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<SecurityPermissionRole> permissions) {
        this.permissions = permissions;
    }

    public Set<SecurityGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<SecurityGroup> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityRole role = (SecurityRole) o;
        return uuid.equals(role.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
