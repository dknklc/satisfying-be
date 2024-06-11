package com.dekankilic.satisfying.mapper;

import com.dekankilic.satisfying.dto.CreateRestaurantRequest;
import com.dekankilic.satisfying.dto.RestaurantResponseDto;
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

    public static RestaurantResponseDto mapToRestaurantResponseDto(Restaurant restaurant){
        return RestaurantResponseDto.builder()
                .name(restaurant.getName())
                .description(restaurant.getName())
                .kitchenType(restaurant.getKitchenType())
                .openingHours(restaurant.getOpeningHours())
                .registrationDate(restaurant.getRegistrationDate())
                .open(restaurant.isOpen())
                .images(restaurant.getImages())
                .contactInformation(restaurant.getContactInformation())
                .address(restaurant.getAddress())
                .owner(restaurant.getOwner())
                .orders(restaurant.getOrders())
                .food(restaurant.getFood())
                .build();
    }
}
