package com.dimitris.restaurant_management.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class RestaurantTag implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public RestaurantTag() {
    }

    public RestaurantTag(Restaurant restaurant, Tag tag) {
        this.restaurant = restaurant;
        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
