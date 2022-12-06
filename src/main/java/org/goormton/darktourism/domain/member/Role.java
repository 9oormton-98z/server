package org.goormton.darktourism.domain.member;

public enum Role {
    
    MEMBER("ROLE_MEMBER"),
    ADMIN("ROLE_ADMIN"),
    ;

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }
}
