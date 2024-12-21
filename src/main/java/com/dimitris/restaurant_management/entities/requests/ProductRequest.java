package com.dimitris.restaurant_management.entities.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
        @NotBlank(message = "Product Name cannot be null.")
        String name,
        @Positive
        Double price,
        @NotBlank
        String category,
        String description
) {
}
