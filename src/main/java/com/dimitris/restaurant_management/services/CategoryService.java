package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Category;
import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.projections.CategoryDTO;
import com.dimitris.restaurant_management.repositories.CategoryRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category createCategory(String categoryName, Restaurant restaurant) {
        Category category = new Category(null,categoryName,restaurant);
        return categoryRepository.save(category);
    }

    @PostAuthorize("returnObject.restaurant.user.username == authentication.name")
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }


    public Optional<Category> findCategoryByNameAndRestaurant(String name, UUID restaurantId) {
        return categoryRepository.findCategoryByNameAndRestaurant_Id(name,restaurantId);
    }


    @PostFilter("filterObject.restaurant.user.username == authentication.name")
    public List<Category> findAllCategoriesByRestaurant(UUID restaurantId) {
        return categoryRepository.findAllByRestaurant_Id(restaurantId);
    }

    public List<Category> findAllCategoriesByRestaurantPublic(UUID restaurantId) {
        return categoryRepository.findAllByRestaurant_Id(restaurantId);
    }

    public List<CategoryDTO> findAllCategoriesByCountAndByRestaurant(UUID restaurantId) {
        return categoryRepository.getCategoriesByProductCount(restaurantId);
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }
}
