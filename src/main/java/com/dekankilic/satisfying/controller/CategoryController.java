package com.dekankilic.satisfying.controller;

import com.dekankilic.satisfying.model.Category;
import com.dekankilic.satisfying.model.User;
import com.dekankilic.satisfying.service.CategoryService;
import com.dekankilic.satisfying.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(category.getName(), user.getId()));
    }

    @GetMapping("/category/restaurant/{id}")
    public ResponseEntity<List<Category>> getRestaurantCategory(@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.findCategoryByRestaurantId(id));
    }
}
