package com.dekankilic.satisfying.dto.request;

import com.dekankilic.satisfying.model.Address;

public record CreateOrderRequest(
        Long restaurantId,
        Address deliveryAddress
) {
}
