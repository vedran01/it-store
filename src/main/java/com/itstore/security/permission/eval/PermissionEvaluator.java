package com.itstore.security.permission.eval;

import com.itstore.security.permission.Permission;

import java.util.HashMap;
import java.util.Map;

public class PermissionEvaluator {

    public static Permission[] evaluate(PermissionEvaluation type, Permission[]... permissions) {

        Map<String, Boolean> result = new HashMap<>();

        for (Permission[] permissionList : permissions) {

            for (Permission permission : permissionList) {

                boolean permitted = permission.isPermitted() > 0;

                if (PermissionEvaluation.MERGE.equals(type) && permitted) {
                    result.put(permission.getPermission(), true);

                } else if (PermissionEvaluation.OVERRIDE.equals(type)) {

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
