package com.dimitris.restaurant_management.entities.requests;

import jakarta.validation.constraints.NotBlank;

public class TagDTO {

    @NotBlank
    private String name;

    public TagDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
