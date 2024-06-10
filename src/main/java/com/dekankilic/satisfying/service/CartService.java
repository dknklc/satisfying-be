package com.dekankilic.satisfying.service;

import com.dekankilic.satisfying.model.Cart;
import com.dekankilic.satisfying.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public Cart saveCart(Cart cart){
        return cartRepository.save(cart);
    }
}
