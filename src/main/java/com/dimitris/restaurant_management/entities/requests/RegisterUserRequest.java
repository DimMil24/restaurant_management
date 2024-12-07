package com.dimitris.restaurant_management.entities.requests;

public record RegisterUserRequest(
        String username,
        String password
) {
}
