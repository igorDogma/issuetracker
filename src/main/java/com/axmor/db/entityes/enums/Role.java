package com.axmor.db.entityes.enums;

public enum Role {
    ROLE_MANAGER("manager"), ROLE_ADMIN("admin");

    private final String role;

    Role(String role) {
        this.role = role;
    }
}
