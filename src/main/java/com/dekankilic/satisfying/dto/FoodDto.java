package com.dekankilic.satisfying.dto;

import com.dekankilic.satisfying.model.Category;
import com.dekankilic.satisfying.model.IngredientItem;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Builder
public record FoodDto (
        String name,
        String description,
        BigDecimal price,
        boolean available,
        boolean isVegetarian,
        boolean isSeasonal,
        Date creatianDate,
        Category foodCategory,
        List<String> images,

        String restaurantName,
        List<IngredientItem> ingredients
) {
}
