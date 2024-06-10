package com.dekankilic.satisfying.mapper;

import com.dekankilic.satisfying.dto.CreateRestaurantRequest;
import com.dekankilic.satisfying.model.Restaurant;

import java.time.LocalDateTime;

public class RestaurantMapper {
    public static Restaurant mapToRestaurant(CreateRestaurantRequest createRestaurantRequest){
        return Restaurant.builder()
                .name(createRestaurantRequest.name())
                .description(createRestaurantRequest.description())
                .kitchenType(createRestaurantRequest.kitchenType())
                .openingHours(createRestaurantRequest.openingHours())
                .images(createRestaurantRequest.images())
                .contactInformation(createRestaurantRequest.contactInformation())
                .registrationDate(LocalDateTime.now())
                .build();
    }
}
