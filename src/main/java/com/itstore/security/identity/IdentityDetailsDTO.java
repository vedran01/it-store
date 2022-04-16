package com.itstore.security.identity;

import com.itstore.security.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

import static com.itstore.security.permission.PermissionResolver.resolve;

@RequiredArgsConstructor
public class IdentityDetailsDTO {

    private final Long id;
    private final String username;
    private final String password;
    private final String email;
    private final Boolean enabled;
    private final Long sId;
    private final String uuid;
    private final String type;
    private final Date validFrom;
    private final Date validTill;
    private final Boolean enabled2fa;
    private final Boolean configured2fa;
    private final String secret2fa;
    private final String groups;
    private final String roles;
    private final String permissions;
    private final String groupPermissions;
    private final String rolePermissions;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Long getsId() {
        return sId;
    }

    public String getUuid() {
        return uuid;
    }

    public String getType() {
        return type;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public Date getValidTill() {
        return validTill;
    }

    public Boolean getEnabled2fa() {
        return enabled2fa;
    }

    public Boolean getConfigured2fa() {
        return configured2fa;
    }

    public String getSecret2fa() {
        return secret2fa;
    }

    public Permission[] getPermissions() {
        IdentityPermission identityPermission = new IdentityPermission(getsId(), null, permissions, groupPermissions, rolePermissions);
        return resolve(identityPermission.getPermissions(), identityPermission.getRolePermissions(), identityPermission.getGroupPermissions());
    }

    public String[] roles() {
        return StringUtils.isNotBlank(roles) ? roles.split(",") : new String[0];
    }

    public String[] groups() {
        return StringUtils.isNotBlank(groups) ? groups.split(",") : new String[0];
    }
}
