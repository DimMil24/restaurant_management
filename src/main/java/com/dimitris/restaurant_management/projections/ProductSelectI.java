package com.dimitris.restaurant_management.projections;

import com.dimitris.restaurant_management.entities.Restaurant;

import java.math.BigDecimal;

public interface ProductSelectI {
    Long getId();
    String getName();
    BigDecimal getPrice();
    Restaurant getRestaurant();
}
