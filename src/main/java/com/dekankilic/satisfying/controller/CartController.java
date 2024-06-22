package com.dekankilic.satisfying.controller;

import com.dekankilic.satisfying.dto.request.AddCartItemRequest;
import com.dekankilic.satisfying.dto.request.UpdateCartItemRequest;
import com.dekankilic.satisfying.model.Cart;
import com.dekankilic.satisfying.model.CartItem;
import com.dekankilic.satisfying.service.CartService;
import com.dekankilic.satisfying.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @PutMapping("/cart-item/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest request, @RequestHeader("Authorization") String jwt){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartService.addItemToCart(request, jwt));
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest request, @RequestHeader("Authorization") String jwt){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartService.updateCartItemQuantity(request.cartItemId(), request.quantity()));
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItemFromCart(@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartService.remoteItemFromCart(id, jwt));
    }

    @PutMapping("/clear")
    public ResponseEntity<Cart> updateCartItemQuantity(@RequestHeader("Authorization") String jwt){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartService.clearCart(jwt));
    }

    @GetMapping
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartService.findCartByUserId(jwt));
    }

}
