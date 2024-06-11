package com.dekankilic.satisfying.controller;

import com.dekankilic.satisfying.dto.RestaurantDto;
import com.dekankilic.satisfying.model.Restaurant;
import com.dekankilic.satisfying.model.User;
import com.dekankilic.satisfying.service.RestaurantService;
import com.dekankilic.satisfying.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization") String jwt, @RequestParam String keyword){
        User user = userService.getUserFromJwt(jwt);
        List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurants);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants(@RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@RequestHeader("Authorization") String jwt, @PathVariable Long id){
        User user = userService.getUserFromJwt(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurant);
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto> addToFavorites(@RequestHeader("Authorization") String jwt, @PathVariable Long id){
        User user = userService.getUserFromJwt(jwt);
        RestaurantDto restaurantDto = restaurantService.addToFavorites(id, user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantDto);
    }
}
