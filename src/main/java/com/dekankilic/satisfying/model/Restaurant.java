package com.dekankilic.satisfying.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String kitchenType;
    private String openingHours;
    private LocalDateTime registrationDate;
    private boolean open;

    @ElementCollection
    @Column(length = 1000)
    private List<String> images;

    @Embedded
    private ContactInformation contactInformation;

    @OneToOne
    private Address address;

    @OneToOne
    private User owner;

    // @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Food> food;

    // @OneToMany
    // private List<IngredientCategory> ingredientCategories;
}
