package com.dekankilic.satisfying.model.elastic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Document(indexName = "restaurants")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestaurantDocument {
    @Id
    private String id;
    private String name;
    private String description;
    private String kitchenType;
    private String openingHours;
    private boolean open;
    private List<String> images;
}
