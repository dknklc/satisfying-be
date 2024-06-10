package com.dekankilic.satisfying.repository;

import com.dekankilic.satisfying.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
