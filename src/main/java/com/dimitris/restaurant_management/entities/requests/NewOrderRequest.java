package com.dimitris.restaurant_management.entities.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record NewOrderRequest(
        @NotNull
        UUID restaurantId,
        @NotNull @Valid
        List<ItemQuantityRequest> itemQuantity
) {
}
