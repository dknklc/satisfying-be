package com.dekankilic.satisfying.service;

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

    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant){
        Food food = FoodMapper.mapToFood(request);
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);

        Food savedFood = foodRepository.save(food);
        restaurant.getFood().add(savedFood);
        return savedFood;

    }

    public boolean deleteFood(Long foodId){
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
        return true;
    }

    public List<Food> getFoodOfRestaurant(Long restaurantId, boolean isVegetarian, boolean isSeasonal, String foodCategory){
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

        return foods;

    }

    public List<Food> searchFood(String keyword){
        return foodRepository.searchFood(keyword);
    }

    public Food findFoodById(Long id){
        return foodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Food", "foodId", id.toString()));
    }

    public Food updateAvailabilityStatus(Long id){
        Food food = findFoodById(id);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }

}
