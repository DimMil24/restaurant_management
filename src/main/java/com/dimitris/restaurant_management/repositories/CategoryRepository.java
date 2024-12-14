package com.dimitris.restaurant_management.repositories;

import com.dimitris.restaurant_management.entities.Category;
import com.dimitris.restaurant_management.projections.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryByNameAndRestaurant_Id(String name, UUID restaurant_id);
    List<Category> findAllByRestaurant_Id(UUID restaurantId);

    @Query("select c.id as id,c.name as name,count(p.Id) as count " +
            "from Category c " +
            "left join Product p on c.id=p.category.id " +
            "where c.restaurant.Id = :restaurantId " +
            "group by c.id")
    List<CategoryDTO> getCategoriesByProductCount(@Param("restaurantId") UUID restaurantId);
}
