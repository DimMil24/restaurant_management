package com.dimitris.restaurant_management.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;
    private String name;
    private boolean isOpen;
    private String description;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;



    public Restaurant() {
    }

    public Restaurant(String name, boolean isOpen, String description,User user) {
        this.name = name;
        this.isOpen = isOpen;
        this.description = description;
        this.user = user;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
