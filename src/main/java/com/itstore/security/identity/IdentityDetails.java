package com.itstore.security.identity;

import com.itstore.core.util.Util;
import com.itstore.security.permission.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public record IdentityDetails(IdentityDetailsDTO identity) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(identity.getPermissions())
                .map(Permission::getPermission)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return identity.getPassword();
    }

    @Override
    public String getUsername() {
        return identity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        Date validTill = identity.getValidTill();
        return validTill == null || Util.isDateAfterNow(validTill);
    }

    @Override
    public boolean isAccountNonLocked() {
        return identity.getEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return identity.getEnabled();
    }
}
