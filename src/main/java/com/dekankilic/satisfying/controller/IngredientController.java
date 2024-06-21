package com.dekankilic.satisfying.controller;

import com.dekankilic.satisfying.dto.request.CreateIngredientCategoryRequest;
import com.dekankilic.satisfying.dto.request.CreateIngredientRequest;
import com.dekankilic.satisfying.model.IngredientCategory;
import com.dekankilic.satisfying.model.IngredientItem;
import com.dekankilic.satisfying.service.IngredientService;
import com.dekankilic.satisfying.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;
    private final UserService userService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody CreateIngredientCategoryRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ingredientService.createIngredientCategory(request.name(), request.restaurantId()));
    }

    @PostMapping
    public ResponseEntity<IngredientItem> createIngredientItem(@RequestBody CreateIngredientRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ingredientService.createIngredientItem(request.restaurantId(), request.ingredientName(), request.categoryId()));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<IngredientItem> updateIngredientStock(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ingredientService.updateStock(id));
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<List<IngredientItem>> getRestaurantIngredients(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ingredientService.findRestaurantIngredients(id));
    }

    @GetMapping("/category/restaurants/{id}")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategories(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ingredientService.findIngredientCategoriesByRestaurantId(id));
    }

}
