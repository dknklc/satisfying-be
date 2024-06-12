package com.dekankilic.satisfying.service;

import com.dekankilic.satisfying.model.elastic.RestaurantDocument;
import com.dekankilic.satisfying.repository.RestaurantDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantDocumentService {
    private final RestaurantDocumentRepository restaurantDocumentRepository;

    public void insertRestaurantDocument(RestaurantDocument restaurantDocument){
        restaurantDocumentRepository.save(restaurantDocument);
    }

}
