package com.dimitris.restaurant_management.entities.requests;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class EditOwnerDTO {
    @NotBlank(message = "Username cannot be empty or blank.")
    private String username;
    @NotBlank
    private String restaurantName;
    @NotBlank
    private String restaurantDesc;
    private List<Long> tags;

    public EditOwnerDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantDesc() {
        return restaurantDesc;
    }

    public void setRestaurantDesc(String restaurantDesc) {
        this.restaurantDesc = restaurantDesc;
    }

    public List<Long> getTags() {
        return tags;
    }

    public void setTags(List<Long> tags) {
        this.tags = tags;
    }
}
