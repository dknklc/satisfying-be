package com.dekankilic.satisfying.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount;
    private String orderStatus;
    private Date createdAt;
    private int totalItem;
    private int totalPrice;

    // private PaymentType paymentType; // add this later

    @ManyToOne
    private Address deliveryAddress;

    @ManyToOne
    private User user;

    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;

    @OneToMany
    private List<OrderItem> items;

}
