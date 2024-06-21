package com.dekankilic.satisfying.service;

import com.dekankilic.satisfying.exception.ResourceNotFoundException;
import com.dekankilic.satisfying.model.IngredientCategory;
import com.dekankilic.satisfying.model.IngredientItem;
import com.dekankilic.satisfying.model.Restaurant;
import com.dekankilic.satisfying.repository.IngredientCategoryRepository;
import com.dekankilic.satisfying.repository.IngredientItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService { // We'll handle both ingredient category and ingredient item related business logics here.

    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final IngredientItemRepository ingredientItemRepository;
    private final RestaurantService restaurantService;

    public IngredientCategory createIngredientCategory(String name, Long restaurantId){
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = IngredientCategory.builder()
                .name(name)
                .restaurant(restaurant)
                .build();

        return ingredientCategoryRepository.save(ingredientCategory);
    }

    public IngredientCategory findIngredientCategoryById(Long id){
        return ingredientCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("IngredientCategory", "ingredientCategoryId", id.toString()));
    }

    public List<IngredientCategory> findIngredientCategoriesByRestaurantId(Long restaurantId){
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        return ingredientCategoryRepository.findByRestaurantId(restaurant.getId());
    }

    public IngredientItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId){
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = findIngredientCategoryById(categoryId);
        IngredientItem item = IngredientItem.builder()
                .name(ingredientName)
                .restaurant(restaurant)
                .category(category)
                .build();

        IngredientItem savedItem = ingredientItemRepository.save(item);
        category.getIngredientItems().add(savedItem);
        return savedItem;
    }

    public List<IngredientItem> findRestaurantIngredients(Long restaurantId){
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    public IngredientItem updateStock(Long id){
        IngredientItem item = ingredientItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("IngredientItem", "ingredientId", id.toString()));
        item.setInStoke(!item.isInStoke());
        return ingredientItemRepository.save(item);
    }
}
