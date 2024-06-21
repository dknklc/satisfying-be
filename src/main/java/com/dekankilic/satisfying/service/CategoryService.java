package com.dekankilic.satisfying.service;

import com.dekankilic.satisfying.exception.ResourceNotFoundException;
import com.dekankilic.satisfying.model.Category;
import com.dekankilic.satisfying.model.Restaurant;
import com.dekankilic.satisfying.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final RestaurantService restaurantService;
    private final CategoryRepository categoryRepository;

    public Category createCategory(String name, Long userId){
        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
        Category category = Category.builder()
                .name(name)
                .restaurant(restaurant)
                .build();

        return categoryRepository.save(category);
    }

    public List<Category> findCategoryByRestaurantId(Long id){
        return categoryRepository.findByRestaurantId(id).orElseThrow(() -> new ResourceNotFoundException("Category", "restaurantId", id.toString()));
    }

    public Category findCategoryById(Long id){
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id.toString()));
    }
}
