package com.dekankilic.satisfying.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_CUSTOMER("CUSTOMER"),
    ROLE_RESTAURANT_OWNER("RESTAURANT_OWNER"),
    ROLE_ADMIN("ADMIN");

    private String value;

    Role(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
