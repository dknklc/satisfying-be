package com.dekankilic.satisfying.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
        String jwt,
        String username,
        String message
) {
}
