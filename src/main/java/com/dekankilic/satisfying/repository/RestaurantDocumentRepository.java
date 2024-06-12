package com.dekankilic.satisfying.repository;

import com.dekankilic.satisfying.model.elastic.RestaurantDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RestaurantDocumentRepository extends ElasticsearchRepository<RestaurantDocument, String> {
}
