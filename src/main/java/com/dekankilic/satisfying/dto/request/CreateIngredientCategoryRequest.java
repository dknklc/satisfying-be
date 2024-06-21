package com.dekankilic.satisfying.dto.request;

import lombok.Builder;

@Builder
public record CreateIngredientCategoryRequest(
        String name,
        Long restaurantId
){
}
