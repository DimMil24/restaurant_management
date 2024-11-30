package com.dimitris.restaurant_management.repositories;

import com.dimitris.restaurant_management.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByName(String name);
    Optional<Product> findByRestaurant_IdAndId(UUID restaurant_id, Long id);
    List<Product> findByRestaurantId(UUID restaurant_id);
}
