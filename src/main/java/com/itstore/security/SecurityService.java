package com.itstore.security;

import com.itstore.security.identity.IdentityPermission;
import com.itstore.security.permission.Permission;
import com.itstore.security.permission.PermissionDao;
import com.itstore.security.permission.eval.PermissionEvaluation;
import com.itstore.security.permission.eval.PermissionEvaluator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final PermissionDao permissionDao;


    public List<PermissionDTO> getPermissions(String userId) {

        List<PermissionDTO> permissionDTOS = new ArrayList<>();

        List<IdentityPermission> permissions = permissionDao.getPermissions(userId, null);

        for (IdentityPermission permission : permissions) {
            String resource = permission.resource();
            String[] evaluate = Arrays.stream(PermissionEvaluator.evaluate(PermissionEvaluation.MERGE, permission.getPermissions(), permission.getRolePermissions(), permission.getGroupPermissions())).map(Permission::getPermission).toArray(String[]::new);

            PermissionDTO permissionDTO = new PermissionDTO(resource, evaluate);
            permissionDTOS.add(permissionDTO);


        }

        return permissionDTOS;
    }

}
