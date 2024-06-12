package com.dekankilic.satisfying.repository;

import com.dekankilic.satisfying.model.outbox.RestaurantOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantOutBoxRepository extends JpaRepository<RestaurantOutbox, Long> {
}
