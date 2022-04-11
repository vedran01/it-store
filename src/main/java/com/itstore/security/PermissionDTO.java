package com.itstore.security;

public class PermissionDTO {

    private String resource;
    private String[] permissions;

    public PermissionDTO(String resource, String[] permissions) {
        this.resource = resource;
        this.permissions = permissions;
    }

    public String getResource() {
        return resource;
    }

    public String[] getPermissions() {
        return permissions;
    }
}
