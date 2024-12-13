package com.dimitris.restaurant_management.entities.requests;

public record ProductRequest(
        String name,
        Double price,
        String category,
        String description
) {
}
