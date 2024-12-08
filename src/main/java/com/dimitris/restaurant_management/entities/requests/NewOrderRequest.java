package com.dimitris.restaurant_management.entities.requests;

import java.util.List;
import java.util.UUID;

public record NewOrderRequest(
        UUID restaurantId,
        List<ItemQuantityRequest> itemQuantity
) {
}
