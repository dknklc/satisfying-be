package com.dekankilic.satisfying.mapper;

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
}
