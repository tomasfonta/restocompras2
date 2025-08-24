package com.tf.restocompras.config.security;

public enum ApplicationPermission {

    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    CREATE("CREATE");

    private final String permission;

    ApplicationPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}