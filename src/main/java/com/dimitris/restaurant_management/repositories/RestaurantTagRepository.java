package com.dimitris.restaurant_management.repositories;

import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.entities.RestaurantTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantTagRepository extends JpaRepository<RestaurantTag, Long> {
    List<RestaurantTag> findAllByRestaurant(Restaurant restaurant);
    Optional<RestaurantTag> findByTag_IdAndRestaurant_Id(Long tagId, UUID restaurantId);
}
