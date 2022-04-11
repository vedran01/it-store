package com.itstore.security.identity;

import com.itstore.security.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public final class IdentityPermission {

    private final static String PERMISSION_DELIMITER = "::";
    private final static String PERMISSION_GRANT_DELIMITER = ":";

    private final Long id;
    private final String resource;
    private final String permissions;
    private final String groupPermissions;
    private final String rolePermissions;

    public Long getId() {
        return id;
    }

    public String resource() {
        return resource;
    }

    public Permission[] getPermissions() {
        return splitPermission(permissions);
    }

    public Permission[] getGroupPermissions() {
        return splitPermission(groupPermissions);
    }

    public Permission[] getRolePermissions() {
        return splitPermission(rolePermissions);
    }

    private Permission[] splitPermission(String permissions) {

        if (StringUtils.isNotBlank(permissions)) {

            String[] permissionGrants = permissions.split(PERMISSION_DELIMITER);

            Permission[] result = new Permission[permissionGrants.length];

            for (int i = 0; i < permissionGrants.length; i++) {

                String[] permissionGrant = permissionGrants[i].split(PERMISSION_GRANT_DELIMITER);

                result[i] = new Permission(permissionGrant[0], Byte.parseByte(permissionGrant[1]));
            }

            return result;
        }

        return new Permission[0];
    }

}
