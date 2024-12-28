package com.dimitris.restaurant_management.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Tag implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<RestaurantTag> restaurants;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RestaurantTag> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<RestaurantTag> restaurants) {
        this.restaurants = restaurants;
    }
}
