package com.dimitris.restaurant_management.entities.requests;

public record RegisterOwnerRequest(String username,
                                   String password,
                                   String restaurantName,
                                   String restaurantDesc) {


}
