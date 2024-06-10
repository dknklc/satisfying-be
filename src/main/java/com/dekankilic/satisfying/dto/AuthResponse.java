package com.dekankilic.satisfying.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String username,
        String message
) {
}
