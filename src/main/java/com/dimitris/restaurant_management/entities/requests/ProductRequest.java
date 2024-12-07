package com.dimitris.restaurant_management.entities.requests;

import com.dimitris.restaurant_management.entities.ProductCategory;

public record ProductRequest(
        String name,
        Double price,
        ProductCategory category,
        String description
) {
}
