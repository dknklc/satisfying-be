package com.dekankilic.satisfying.dto;

import lombok.Builder;

@Builder
public record LoginRequest (
        String username,
        String password
){
}
