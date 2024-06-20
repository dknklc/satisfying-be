package com.dekankilic.satisfying.dto.request;

import com.dekankilic.satisfying.model.Category;
import com.dekankilic.satisfying.model.IngredientItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record CreateFoodRequest(
        String name,
        String description,
        BigDecimal price,
        boolean available,
        boolean isVegetarian,
        boolean isSeasonal,
        Date creatianDate,
        Category foodCategory,
        List<String> images,

        Long restaurantId,
        List<IngredientItem> ingredients
) {
}
