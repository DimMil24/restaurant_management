package com.dimitris.restaurant_management.entities;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Product {

    @Id @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private Boolean isAvailable = true;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Product() {
    }

    public Product(String name, BigDecimal price,Category category, Boolean isAvailable, String description, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.isAvailable = isAvailable;
        this.description = description;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}
