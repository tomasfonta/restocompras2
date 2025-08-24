package com.tf.restocompras.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationRoles {
    ADMIN(Set.of(
            ApplicationPermission.CREATE,
            ApplicationPermission.DELETE,
            ApplicationPermission.READ,
            ApplicationPermission.UPDATE)),
    USER(Set.of(
            ApplicationPermission.CREATE,
            ApplicationPermission.DELETE,
            ApplicationPermission.READ,
            ApplicationPermission.UPDATE));

    private final Set<ApplicationPermission> permissions;

    ApplicationRoles(Set<ApplicationPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationPermission> getPermissions() {
        return permissions;
    }

    public Set<GrantedAuthority> getGrantedAuthorities() {
        return getPermissions()
                .stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
    }
}