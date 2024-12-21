package com.dimitris.restaurant_management.entities.requests;

import jakarta.validation.constraints.Positive;

public record ItemQuantityRequest(
        Long id,
        @Positive(message = "Quantity Cannot be 0")
        Long quantity
) {
}
