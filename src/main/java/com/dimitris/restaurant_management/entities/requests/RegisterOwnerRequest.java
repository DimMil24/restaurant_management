package com.dimitris.restaurant_management.entities.requests;

public class RegisterOwnerRequest {

    public String username;
    public String password;
    public String restaurantName;
    public String restaurantDesc;

    public RegisterOwnerRequest(String username, String password, String restaurantName, String restaurantDesc) {
        this.username = username;
        this.password = password;
        this.restaurantName = restaurantName;
        this.restaurantDesc = restaurantDesc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
