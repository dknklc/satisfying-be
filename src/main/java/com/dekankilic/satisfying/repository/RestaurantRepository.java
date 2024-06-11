package com.dekankilic.satisfying.repository;

import com.dekankilic.satisfying.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(value = "SELECT R FROM Restaurant R WHERE lower(R.name) LIKE lower(concat('%', :query, '%')) OR lower(r.kitchenType) LIKE lower(concat('%', :query, '%'))", nativeQuery = true) // TODO : Use Elasticsearch for searching restaurants and food later.
    List<Restaurant> findBySearchQuery(String query);

    Optional<Restaurant> findByOwnerId(Long userId);
}
