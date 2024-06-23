package com.dekankilic.satisfying.repository;

import com.dekankilic.satisfying.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
