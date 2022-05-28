package com.itstore.security.model;

import com.itstore.core.data.model.AbstractEntity;
import com.itstore.security.identity.IdentityType;
import com.itstore.model.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "sec_identity")
public class SecurityIdentity extends AbstractEntity {

    @OneToOne(mappedBy = "identity")
    private User user;

    @Enumerated(EnumType.STRING)
    private IdentityType type;

    private String uuid;

    private Date validFrom;

    private Date validTill;

    private Date lastLogin;

    private Date passwordChanged;

    @Column(name = "enabled_2fa")
    private boolean enabled2fa;

    @Column(name = "configured_2fa")
    private boolean configured2fa;

    @Column(name = "secret_2fa")
    private String secret2fa;

    public SecurityIdentity() {
        uuid = UUID.randomUUID().toString();
    }

    @OneToMany(mappedBy = "identity", orphanRemoval = true)
    private Set<SecurityPermissionIdentity> permissions = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "sec_user_role",
            joinColumns = @JoinColumn(name = "identity_id") ,
            inverseJoinColumns = @JoinColumn(name = "role_id") )
    private Set<SecurityRole> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "sec_user_group",
            joinColumns = @JoinColumn(name = "identity_id") ,
            inverseJoinColumns = @JoinColumn(name = "group_id") )
    private Set<SecurityGroup> groups = new HashSet<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public IdentityType getType() {
        return type;
    }

    public void setType(IdentityType type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTill() {
        return validTill;
    }

    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLoginDt) {
        this.lastLogin = lastLoginDt;
    }

    public Date getPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(Date passwordChangeDt) {
        this.passwordChanged = passwordChangeDt;
    }

    public boolean isEnabled2fa() {
        return enabled2fa;
    }

    public void setEnabled2fa(boolean enabled2fa) {
        this.enabled2fa = enabled2fa;
    }

    public boolean isConfigured2fa() {
        return configured2fa;
    }

    public void setConfigured2fa(boolean configured2fa) {
        this.configured2fa = configured2fa;
    }

    public String getSecret2fa() {
        return secret2fa;
    }

    public void setSecret2fa(String secret2fa) {
        this.secret2fa = secret2fa;
    }

    public Set<SecurityPermissionIdentity> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<SecurityPermissionIdentity> permissions) {
        this.permissions = permissions;
    }

    public Set<SecurityRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SecurityRole> roles) {
        this.roles = roles;
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
        SecurityIdentity that = (SecurityIdentity) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
