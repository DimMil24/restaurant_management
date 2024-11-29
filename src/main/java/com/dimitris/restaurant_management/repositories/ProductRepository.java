package com.dimitris.restaurant_management.repositories;

import com.dimitris.restaurant_management.entities.Product;
import com.dimitris.restaurant_management.projections.ProductSelectI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByName(String name);

    @Query("select u from Product u")
    Collection<ProductSelectI> findAllFiltered();
    Optional<Product> findByRestaurant_IdAndId(UUID restaurant_id, Long id);

}
