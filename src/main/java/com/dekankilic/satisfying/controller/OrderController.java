package com.dekankilic.satisfying.controller;

import com.dekankilic.satisfying.dto.request.CreateOrderRequest;
import com.dekankilic.satisfying.dto.response.PaymentResponse;
import com.dekankilic.satisfying.model.Order;
import com.dekankilic.satisfying.model.User;
import com.dekankilic.satisfying.service.OrderService;
import com.dekankilic.satisfying.service.PaymentService;
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
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createOrder(@RequestBody CreateOrderRequest request, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        Order order = orderService.createOrder(request, user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.createPaymentLink(order));
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> getAllOrdersOfUser(@RequestHeader("Authorization") String jwt){
        User user = userService.getUserFromJwt(jwt);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getOrdersOfUser(user.getId()));
    }
}
