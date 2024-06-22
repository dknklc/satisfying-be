package com.dekankilic.satisfying.dto.request;

public record UpdateCartItemRequest(
        Long cartItemId,
        int quantity
) {
}
