package com.dekankilic.satisfying.repository;

import com.dekankilic.satisfying.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByCustomerId(Long userId);
}
