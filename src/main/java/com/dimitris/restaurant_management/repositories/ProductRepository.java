package com.dimitris.restaurant_management.repositories;

import com.dimitris.restaurant_management.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByNameAndRestaurant_Id(String name, UUID restaurant_id);
    Optional<Product> findByRestaurant_IdAndId(UUID restaurant_id, Long id);
    List<Product> findByRestaurantId(UUID restaurant_id);
}
