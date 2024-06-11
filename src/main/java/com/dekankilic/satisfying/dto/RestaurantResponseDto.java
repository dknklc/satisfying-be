package com.dekankilic.satisfying.dto;

import com.dekankilic.satisfying.model.*;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record RestaurantResponseDto(
        String name,
        String description,
        String kitchenType,
        String openingHours,
        LocalDateTime registrationDate,
        boolean open,
        List<String> images,
        ContactInformation contactInformation,
        Address address,
        User owner,
        List<Order> orders,
        List<Food> food
) {
}
