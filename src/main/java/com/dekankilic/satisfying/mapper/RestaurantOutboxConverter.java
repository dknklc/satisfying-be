package com.dekankilic.satisfying.mapper;

import com.dekankilic.satisfying.model.Restaurant;
import com.dekankilic.satisfying.model.outbox.RestaurantOutbox;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.UUID;

@UtilityClass
public class RestaurantOutboxConverter {
    private final ObjectMapper MAPPER = new ObjectMapper();

    public static RestaurantOutbox convertToOutbox(Restaurant restaurant){
        try {
            String payload = MAPPER.writeValueAsString(restaurant);
            return RestaurantOutbox.builder()
                    .aggregateId(String.valueOf(UUID.randomUUID()))
                    .aggregateType(Restaurant.class.getName())
                    .messageType("Account Created")
                    .payload(payload)
                    .createDate(LocalDateTime.now())
                    .build();

        }catch (JsonProcessingException e){
            throw new RuntimeException();
        }
    }
}
