package com.itstore.security.permission;


import java.util.HashMap;
import java.util.Map;

import static com.itstore.security.permission.PermissionResolver.ResolverMethod.*;

public class PermissionResolver {

    public enum ResolverMethod {
        UNANIMOUS, AFFIRMATIVE
    }

    public static Permission[] resolve(Permission[] ...permissions) {
        return resolve(UNANIMOUS, permissions);
    }

    public static Permission[] resolve(ResolverMethod type, Permission[]... permissions) {

        Map<String, Boolean> result = new HashMap<>();

        for (Permission[] permissionList : permissions) {

            for (Permission permission : permissionList) {

                boolean permitted = permission.isPermitted() > 0;

                if (AFFIRMATIVE.equals(type) && permitted) {
                    result.put(permission.getPermission(), true);

                } else if (UNANIMOUS.equals(type)) {

                    if (!result.containsKey(permission.getPermission())) {
                        result.put(permission.getPermission(), permitted);
                    }
                }
            }
        }

        return result.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(permission -> new Permission(permission.getKey(), (byte) 1))
                .toArray(Permission[]::new);
    }
}
