package com.dekankilic.satisfying.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        String jwt,
        String username,
        String message
) {
}
