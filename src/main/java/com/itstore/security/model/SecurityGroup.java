package com.itstore.security.model;

import com.itstore.core.data.model.AbstractEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "sec_group")
public class SecurityGroup extends AbstractEntity {

    private String name;
    private String key;
    private String uuid;
    private boolean enabled;

    @ManyToMany(mappedBy = "groups")
    private Set<SecurityIdentity> identities = new HashSet<>();

    @OneToMany(mappedBy = "group", orphanRemoval = true)
    private Set<SecurityPermissionGroup> permissions = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "sec_group_role",
            joinColumns = @JoinColumn(name = "group_id") ,
            inverseJoinColumns = @JoinColumn(name = "role_id") )
    private Set<SecurityRole> roles = new HashSet<>();

    public SecurityGroup() {
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

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<SecurityPermissionGroup> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<SecurityPermissionGroup> permissions) {
        this.permissions = permissions;
    }

    public Set<SecurityIdentity> getIdentities() {
        return identities;
    }

    public void setIdentities(Set<SecurityIdentity> identities) {
        this.identities = identities;
    }

    public Set<SecurityRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SecurityRole> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityGroup group = (SecurityGroup) o;
        return uuid.equals(group.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
