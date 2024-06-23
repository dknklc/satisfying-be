package com.dekankilic.satisfying.dto.request;

import com.dekankilic.satisfying.model.Role;
import lombok.Builder;

import java.util.Set;

@Builder
public record RegisterRequest(
        String name,
        String username,
        String email,
        String password,
        Set<Role> authorities
) {
}
