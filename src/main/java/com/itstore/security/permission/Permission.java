package com.itstore.security.permission;

import java.util.Objects;

public final class Permission {

    public String permission;
    public byte permitted;

    public Permission() {
    }

    public Permission(String permission, byte permitted) {
        this.permission = permission;
        this.permitted = permitted;
    }

    public String getPermission() {
        return permission;
    }

    public byte isPermitted() {
        return permitted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(permission, that.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permission);
    }
}
