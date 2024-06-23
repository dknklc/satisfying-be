package com.dekankilic.satisfying.controller;

import com.dekankilic.satisfying.dto.request.CreateOrderRequest;
import com.dekankilic.satisfying.model.Order;
import com.dekankilic.satisfying.model.User;
import com.dekankilic.satisfying.service.OrderService;
import com.dekankilic.satisfying.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(request, user));
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> getAllOrdersOfUser(@RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getOrdersOfUser(user.getId()));
    }
}
