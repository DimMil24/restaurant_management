package com.dimitris.restaurant_management.repositories;

import com.dimitris.restaurant_management.entities.RestaurantTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantTagRepository extends JpaRepository<RestaurantTag, Long> {
}
