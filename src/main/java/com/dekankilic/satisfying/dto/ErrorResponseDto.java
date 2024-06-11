package com.dekankilic.satisfying.dto;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        String apiPath,
        HttpStatus errorCode,
        String errorMessage,
        LocalDateTime errorTime
) {
}
