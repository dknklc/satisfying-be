package com.dekankilic.satisfying.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    ROLE_CUSTOMER,
    ROLE_RESTAURANT_OWNER,
    ROLE_ADMIN;

    private String value;

    @Override
    public String getAuthority() {
        return null;
    }
}
