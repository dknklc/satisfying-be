package com.dekankilic.satisfying.controller;

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
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Order>> getAllOrdersOfRestaurant(@PathVariable Long id, @RequestParam(required = false) String orderStatus, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getOrdersOfRestaurant(id, orderStatus));
    }

    @PatchMapping("/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @PathVariable String orderStatus, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.updateOrder(orderId, orderStatus));
    }
}
