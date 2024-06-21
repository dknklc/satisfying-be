package com.dekankilic.satisfying.dto.request;

public record CreateIngredientRequest(
        Long restaurantId,
        String ingredientName,
        Long categoryId
) {
}
