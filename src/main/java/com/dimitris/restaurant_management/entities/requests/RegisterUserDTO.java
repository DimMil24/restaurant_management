package com.dimitris.restaurant_management.entities.requests;

import jakarta.validation.constraints.NotBlank;

public class RegisterUserDTO {

    @NotBlank(message = "Username cannot be null")
    String username;
    @NotBlank(message = "Password cannot be null")
    String password;

    public RegisterUserDTO() {
    }

    public RegisterUserDTO(String username, String password) {
        this.username = username;
        this.password = password;
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
}
