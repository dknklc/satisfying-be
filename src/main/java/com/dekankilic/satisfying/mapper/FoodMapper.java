package com.dekankilic.satisfying.mapper;

import com.dekankilic.satisfying.dto.FoodDto;
import com.dekankilic.satisfying.dto.request.CreateFoodRequest;
import com.dekankilic.satisfying.model.Food;

import java.sql.Date;
import java.time.LocalDate;

public class FoodMapper {

    public static Food mapToFood(CreateFoodRequest request){
        return Food.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .available(request.available())
                .isVegetarian(request.isVegetarian())
                .isSeasonal(request.isSeasonal())
                .creatianDate(Date.valueOf(LocalDate.now()))
                .images(request.images())
                .build();
    }

    public static FoodDto mapToFoodDto(Food food){
        return FoodDto.builder()
                .name(food.getName())
                .description(food.getDescription())
                .price(food.getPrice())
                .available(food.isAvailable())
                .isVegetarian(food.isVegetarian())
                .isSeasonal(food.isSeasonal())
                .creatianDate(food.getCreatianDate())
                .foodCategory(food.getFoodCategory())
                .images(food.getImages())
                .restaurantName(food.getRestaurant().getName())
                .ingredients(food.getIngredients())
                .build();
    }
}
