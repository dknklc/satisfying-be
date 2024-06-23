package com.dekankilic.satisfying.dto.request;

import lombok.Builder;

@Builder
public record LoginRequest (
        String username,
        String password
){
}
