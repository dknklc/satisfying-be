package com.dekankilic.satisfying.service;

import com.dekankilic.satisfying.dto.request.AddCartItemRequest;
import com.dekankilic.satisfying.exception.ResourceNotFoundException;
import com.dekankilic.satisfying.model.Cart;
import com.dekankilic.satisfying.model.CartItem;
import com.dekankilic.satisfying.model.Food;
import com.dekankilic.satisfying.model.User;
import com.dekankilic.satisfying.repository.CartItemRepository;
import com.dekankilic.satisfying.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final FoodService foodService;

    public Cart saveCart(Cart cart){
        return cartRepository.save(cart);
    }

    public CartItem addItemToCart(AddCartItemRequest request, String jwt){
        User user = userService.getUserFromJwt(jwt); // in which users' cart, we want to add this item, so first find the user
        Food food = foodService.findFoodById(request.foodId()); // find the food that we want to add as cartitem
        Cart cart = cartRepository.findByCustomerId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Cart", "withCustomerId", user.getId().toString())); // find the cart

        for(CartItem cartItem : cart.getCartItems()){
            if(cartItem.getFood().equals(food)){                                   // If the cartItem already presents in the cart, we need to just update its quantity, otherwise add cart item to the cart.
                int newQuantity = cartItem.getQuantity() + request.quantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        BigDecimal totalPrice = new BigDecimal(request.quantity());
        totalPrice = totalPrice.multiply(food.getPrice());
        CartItem newCartItem = CartItem.builder()
                .quantity(request.quantity())
                .ingredients(request.ingredients())
                .totalPrice(totalPrice)
                .food(food)
                .cart(cart)
                .build();
        CartItem savedCartItem = cartItemRepository.save(newCartItem);
        cart.getCartItems().add(savedCartItem);

        return savedCartItem;
    }

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("CartItem", "cartItemId", cartItemId.toString()));
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice().multiply(new BigDecimal(quantity)));

        return cartItemRepository.save(cartItem);
    }

    public Cart remoteItemFromCart(Long cartItemId, String jwt){
        User user = userService.getUserFromJwt(jwt); // in which users' cart, we want to add this item, so first find the user
        Cart cart = cartRepository.findByCustomerId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Cart", "withCustomerId", user.getId().toString())); // find the cart
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("CartItem", "cartItemId", cartItemId.toString()));

        cart.getCartItems().remove(cartItem);

        return cartRepository.save(cart);
    }

    public BigDecimal calculateCartTotal(Cart cart){
        BigDecimal total = BigDecimal.ZERO;
        for(CartItem cartItem : cart.getCartItems()){
            total  = total.add(cartItem.getFood().getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
        }

        return total;
    }

    public Cart findCartById(Long id){
        return cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", id.toString()));
    }

    public Cart findCartByUserId(String jwt){
        Long userId = userService.getUserFromJwt(jwt).getId();
        return cartRepository.findByCustomerId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart", "userId", userId.toString()));
    }

    public Cart clearCart(String jwt){
        Cart cart = findCartByUserId(jwt);
        cart.getCartItems().clear();

        return cartRepository.save(cart);
    }

}
