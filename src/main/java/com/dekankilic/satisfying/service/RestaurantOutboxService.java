package com.dekankilic.satisfying.service;

import com.dekankilic.satisfying.model.outbox.RestaurantOutbox;
import com.dekankilic.satisfying.repository.RestaurantOutBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantOutboxService {
    private final RestaurantOutBoxRepository restaurantOutBoxRepository;

    public void saveOutbox(RestaurantOutbox restaurantOutbox){
        restaurantOutBoxRepository.save(restaurantOutbox);
    }
}
