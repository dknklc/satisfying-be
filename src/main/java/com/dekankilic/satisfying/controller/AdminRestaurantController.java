package com.dekankilic.satisfying.controller;

import com.dekankilic.satisfying.dto.CreateRestaurantRequest;
import com.dekankilic.satisfying.dto.RestaurantResponseDto;
import com.dekankilic.satisfying.model.Restaurant;
import com.dekankilic.satisfying.model.User;
import com.dekankilic.satisfying.service.RestaurantService;
import com.dekankilic.satisfying.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/restaurants")
@RequiredArgsConstructor
public class AdminRestaurantController {

    private final RestaurantService restaurantService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RestaurantResponseDto> createRestaurant(@RequestBody CreateRestaurantRequest request, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        RestaurantResponseDto restaurant = restaurantService.createRestaurant(request, user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(restaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> updateRestaurant(@RequestBody CreateRestaurantRequest request, @RequestHeader("Authorization") String jwt, @PathVariable Long id){
        User user = userService.getUserFromJwt(jwt);
        RestaurantResponseDto restaurant = restaurantService.updateRestaurant(id, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@RequestHeader("Authorization") String jwt, @PathVariable Long id){
        boolean isDeleted = false;
        User user = userService.getUserFromJwt(jwt);
        isDeleted = restaurantService.deleteRestaurant(id);
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Restaurant with id {} is deleted successfully" + id);
        }
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Restaurant with id {} cannot be deleted" + id);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<RestaurantResponseDto> updateRestaurantStatus(@RequestHeader("Authorization") String jwt, @PathVariable Long id){
        User user = userService.getUserFromJwt(jwt);
        RestaurantResponseDto restaurant = restaurantService.updateRestaurantStatus(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurant);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurant);
    }
}
