package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Product;
import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.entities.RestaurantTag;
import com.dimitris.restaurant_management.entities.Tag;
import com.dimitris.restaurant_management.repositories.ProductRepository;
import com.dimitris.restaurant_management.repositories.RestaurantRepository;
import com.dimitris.restaurant_management.repositories.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ProductRepository productRepository;
    private final TagRepository tagRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, ProductRepository productRepository, TagRepository tagRepository) {
        this.restaurantRepository = restaurantRepository;
        this.productRepository = productRepository;
        this.tagRepository = tagRepository;
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
    public Restaurant addRestaurant(String name, String description) {
        Restaurant restaurant = new Restaurant(name,true, description, null);
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void associateRestaurantToTags(Restaurant restaurant, List<Long> tags) {
        List<RestaurantTag> tagsToSave = new ArrayList<>();
        if (tags != null)  {
            for (Long tagId : tags) {
                Optional<Tag> tagOptional = tagRepository.findById(tagId);
                if (tagOptional.isPresent()) {
                    Tag tag = tagOptional.get();
                    tagsToSave.add(new RestaurantTag(restaurant, tag));
                }
            }
        }
        restaurant.setTags(tagsToSave);
        restaurantRepository.save(restaurant);
    }

    public boolean findRestaurantByName(String name) {
        return restaurantRepository.findByName(name).isPresent();
    }
}
