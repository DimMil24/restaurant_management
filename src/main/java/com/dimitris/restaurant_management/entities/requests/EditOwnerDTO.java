package com.dimitris.restaurant_management.entities.requests;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class EditOwnerDTO {
    @NotBlank(message = "Restaurant Name cannot be empty or blank.")
    private String restaurantName;
    @NotBlank(message = "Description cannot be empty or blank.")
    private String restaurantDesc;
    private List<Long> tags;

    public EditOwnerDTO() {
    }

    public EditOwnerDTO(String restaurantName, String restaurantDesc) {
        this.restaurantName = restaurantName;
        this.restaurantDesc = restaurantDesc;
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
