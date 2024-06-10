package com.dekankilic.satisfying.dto;

import com.dekankilic.satisfying.model.Address;
import com.dekankilic.satisfying.model.ContactInformation;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateRestaurantRequest(
        String name,
        String description,
        String kitchenType,
        String openingHours,
        List<String>images,
        Address address,
        ContactInformation contactInformation
) {
}
