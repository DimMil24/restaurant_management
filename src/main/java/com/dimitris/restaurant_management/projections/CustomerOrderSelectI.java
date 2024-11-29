package com.dimitris.restaurant_management.projections;

import java.math.BigDecimal;

public interface CustomerOrderSelectI {
    String getName();
    BigDecimal getPrice();
    Integer getQuantity();
}
