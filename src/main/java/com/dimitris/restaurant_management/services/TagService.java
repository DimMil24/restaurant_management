package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Tag;
import com.dimitris.restaurant_management.repositories.RestaurantTagRepository;
import com.dimitris.restaurant_management.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


//TODO: THIS IS ONLY FOR ADMIN ROLE. LEAVE IT FOR EVERYONE FOR TESTING
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final RestaurantTagRepository restaurantTagRepository;

    public TagService(TagRepository tagRepository, RestaurantTagRepository restaurantTagRepository) {
        this.tagRepository = tagRepository;
        this.restaurantTagRepository = restaurantTagRepository;
    }

    public Optional<Tag> getTagById(long id) {
        return tagRepository.findById(id);
    }
    public Optional<Tag> getTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public void createNewTag(String name) {
        tagRepository.save(new Tag(name));
    }

    public Tag updateTag(long id, Tag tag) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if (tagOptional.isPresent()) {
            Tag tagToUpdate = tagOptional.get();
            tagToUpdate.setName(tag.getName());
            return tagRepository.save(tagToUpdate);
        }
        return null;
    }

    public void deleteTag(long id) {
        tagRepository.deleteById(id);
    }

    public List<Tag> getTagsByRestaurantId(UUID restaurantId) {
        //TODO
        return List.of();
    }
}
