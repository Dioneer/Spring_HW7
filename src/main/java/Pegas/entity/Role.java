package Pegas.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    admin, user;

    @Override
    public String getAuthority() {
        return name();
    }
}
