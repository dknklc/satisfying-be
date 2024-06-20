package com.dekankilic.satisfying.controller;

import com.dekankilic.satisfying.model.Food;
import com.dekankilic.satisfying.model.User;
import com.dekankilic.satisfying.service.FoodService;
import com.dekankilic.satisfying.service.RestaurantService;
import com.dekankilic.satisfying.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;
    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(foodService.searchFood(keyword));
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Food>> searchFood(@PathVariable Long id, @RequestParam boolean vegetarian, @RequestParam boolean seasonal, @RequestParam(required = false) String foodCategory, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(foodService.getFoodOfRestaurant(id, vegetarian, seasonal, foodCategory));
    }
}
