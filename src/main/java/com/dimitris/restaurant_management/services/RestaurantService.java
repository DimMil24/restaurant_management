package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Product;
import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.repositories.ProductRepository;
import com.dimitris.restaurant_management.repositories.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ProductRepository productRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, ProductRepository productRepository) {
        this.restaurantRepository = restaurantRepository;
        this.productRepository = productRepository;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(UUID id) {
        return restaurantRepository.findById(id).orElseThrow();
    }

    public List<Product> getProductsByRestaurant(UUID restaurant_id) {
        return productRepository.findByRestaurantId(restaurant_id);
    }

    @Transactional
    public Restaurant addRestaurant(String name, String description, User user  ) {
        Restaurant restaurant = new Restaurant(name,true, description, user);
        return restaurantRepository.save(restaurant);
    }
}
