package com.dekankilic.satisfying.repository;

import com.dekankilic.satisfying.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);
    List<Order> findByRestaurantId(Long restaurantId);
}
