package com.dimitris.restaurant_management.repositories;

import com.dimitris.restaurant_management.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    Optional<Restaurant> findByName(String name);

    @Query("select distinct r from Restaurant r join r.tags t where t.tag.name in :tagNames")
    List<Restaurant> findRestaurantsByTag(@Param("tagNames") String[] tagNames);
}
