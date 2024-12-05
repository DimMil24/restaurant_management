package com.dimitris.restaurant_management.entities.responses;

import java.math.BigDecimal;

public record ProductResponse(
        String name,
        BigDecimal price,
        String category,
        String description
) {
}
