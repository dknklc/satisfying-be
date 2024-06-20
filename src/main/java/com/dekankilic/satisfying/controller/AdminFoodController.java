package com.dekankilic.satisfying.controller;

import com.dekankilic.satisfying.dto.FoodDto;
import com.dekankilic.satisfying.dto.request.CreateFoodRequest;
import com.dekankilic.satisfying.model.Restaurant;
import com.dekankilic.satisfying.model.User;
import com.dekankilic.satisfying.service.FoodService;
import com.dekankilic.satisfying.service.RestaurantService;
import com.dekankilic.satisfying.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
@RequiredArgsConstructor
public class AdminFoodController {
    private final FoodService foodService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<FoodDto> createFood(@RequestBody CreateFoodRequest request, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(request.restaurantId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(foodService.createFood(request, request.foodCategory(), restaurant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        boolean result = foodService.deleteFood(id);
        if(result){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("The food with id : {} deleted successfully" + id);
        } else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body("The food with id : {} cannot be deleted" + id);
        }
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<FoodDto> updateFoodAvailability(@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(foodService.updateAvailabilityStatus(id));
    }
}
