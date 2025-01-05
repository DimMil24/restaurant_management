package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Product;
import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.entities.RestaurantTag;
import com.dimitris.restaurant_management.entities.Tag;
import com.dimitris.restaurant_management.entities.requests.EditOwnerDTO;
import com.dimitris.restaurant_management.repositories.ProductRepository;
import com.dimitris.restaurant_management.repositories.RestaurantRepository;
import com.dimitris.restaurant_management.repositories.RestaurantTagRepository;
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
    private final RestaurantTagRepository restaurantTagRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, ProductRepository productRepository, TagRepository tagRepository, RestaurantTagRepository restaurantTagRepository) {
        this.restaurantRepository = restaurantRepository;
        this.productRepository = productRepository;
        this.tagRepository = tagRepository;
        this.restaurantTagRepository = restaurantTagRepository;
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


    @Transactional
    public void editRestaurant(EditOwnerDTO editOwnerDTO, Restaurant restaurant) {
        restaurant.setName(editOwnerDTO.getRestaurantName());
        restaurant.setDescription(editOwnerDTO.getRestaurantDesc());
        updateTagsToRestaurant(restaurant, editOwnerDTO.getTags());
        restaurantRepository.save(restaurant);
    }

    @Transactional
    public void updateTagsToRestaurant(Restaurant restaurant, List<Long> tags) {
        var restaurantTags = restaurantTagRepository.findAllByRestaurant(restaurant);
        var currentTags = restaurantTags.stream().map(RestaurantTag::getTag).map(Tag::getId).toList();
        if (tags != null)  {
            List<RestaurantTag> tagsToSave = new ArrayList<>();
            var tagsToAdd = tags.stream()
                    .filter(tag -> !currentTags.contains(tag))
                    .toList();

            for (Long tagId : tagsToAdd) {
                Optional<Tag> tagOptional = tagRepository.findById(tagId);
                if (tagOptional.isPresent()) {
                    Tag tag = tagOptional.get();
                    tagsToSave.add(new RestaurantTag(restaurant, tag));
                }
            }
            restaurantTagRepository.saveAll(tagsToSave);
        }
        List<Long> tagsToRemove;
        if (tags == null)  {
            tagsToRemove = currentTags;
        } else {
            tagsToRemove = currentTags.stream().filter(tag -> !tags.contains(tag)).toList();
        }

        for (Long tagId : tagsToRemove) {
            Optional<RestaurantTag> tagOptional = restaurantTagRepository.findByTag_Id(tagId);
            if (tagOptional.isPresent()) {
                restaurantTagRepository.delete(tagOptional.get());
            }
        }
    }
}
