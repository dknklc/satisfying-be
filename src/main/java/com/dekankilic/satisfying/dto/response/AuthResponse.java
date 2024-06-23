package com.dekankilic.satisfying.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(
        String username,
        String message
) {
}
