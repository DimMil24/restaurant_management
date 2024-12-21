package com.dimitris.restaurant_management.entities.requests;

import jakarta.validation.constraints.NotBlank;

public record RegisterOwnerRequest(
        @NotBlank(message = "Username cannot be empty or blank.")
        String username,
        @NotBlank
        String password,
        @NotBlank
        String restaurantName,
        @NotBlank
        String restaurantDesc
) { }
