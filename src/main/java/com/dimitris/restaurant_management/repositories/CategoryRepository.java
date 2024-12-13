package com.dimitris.restaurant_management.repositories;

import com.dimitris.restaurant_management.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryByName(String name);

    List<Category> findAllByRestaurant_Id(UUID restaurantId);
}
