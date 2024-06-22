package com.dekankilic.satisfying.service;

import com.dekankilic.satisfying.dto.FoodDto;
import com.dekankilic.satisfying.dto.request.CreateFoodRequest;
import com.dekankilic.satisfying.exception.ResourceNotFoundException;
import com.dekankilic.satisfying.mapper.FoodMapper;
import com.dekankilic.satisfying.model.Category;
import com.dekankilic.satisfying.model.Food;
import com.dekankilic.satisfying.model.Restaurant;
import com.dekankilic.satisfying.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final CategoryService categoryService;

    public FoodDto createFood(CreateFoodRequest request, Category category, Restaurant restaurant){
        // First, save the category
        List<Category> listOfCategories = categoryService.findCategoryByRestaurantId(restaurant.getId());
        category.setRestaurant(restaurant);
        if(!listOfCategories.contains(category))
            categoryService.createCategory(category.getName(), restaurant.getOwner().getId());

        Food food = FoodMapper.mapToFood(request);
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);

        Food savedFood = foodRepository.save(food);
        restaurant.getFood().add(savedFood);

        return FoodMapper.mapToFoodDto(savedFood);

    }

    public boolean deleteFood(Long foodId){
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
        return true;
    }

    public List<FoodDto> getFoodOfRestaurant(Long restaurantId, boolean isVegetarian, boolean isSeasonal, String foodCategory){
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
        if(isVegetarian){
            foods = foods.stream().filter(Food::isVegetarian).collect(Collectors.toList());
        }
        if(isSeasonal){
            foods = foods.stream().filter(Food::isSeasonal).collect(Collectors.toList());
        }
        if(foodCategory != null && !foodCategory.equals("")){
            foods = foods.stream().filter(food -> {
                if (food.getFoodCategory() != null){
                    return food.getFoodCategory().getName().equals(foodCategory);
                }
                return false;
            }).collect(Collectors.toList());
        }

        return foods.stream().map(FoodMapper::mapToFoodDto).collect(Collectors.toList());

    }

    public List<FoodDto> searchFood(String keyword){
        List<Food> foods = foodRepository.searchFood(keyword);
        return foods.stream().map(FoodMapper::mapToFoodDto).collect(Collectors.toList());
    }

    public Food findFoodById(Long id){
        return foodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Food", "foodId", id.toString()));
    }

    public FoodDto updateAvailabilityStatus(Long id){
        Food food = findFoodById(id);
        food.setAvailable(!food.isAvailable());
        Food savedFood = foodRepository.save(food);
        return FoodMapper.mapToFoodDto(savedFood);
    }

}
