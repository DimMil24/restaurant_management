package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.RestaurantTag;
import com.dimitris.restaurant_management.entities.Tag;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.entities.requests.EditOwnerDTO;
import com.dimitris.restaurant_management.services.RestaurantService;
import com.dimitris.restaurant_management.services.TagService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final TagService tagService;
    private final RestaurantService restaurantService;

    public ProfileController(TagService tagService, RestaurantService restaurantService) {
        this.tagService = tagService;
        this.restaurantService = restaurantService;
    }

    @ModelAttribute("tags")
    public List<Tag> getTags() {
        return tagService.findAll();
    }

    @ModelAttribute("user")
    public User getUser(@AuthenticationPrincipal User user) {
        return user;
    }

    @ModelAttribute("restaurantTags")
    public List<RestaurantTag> getRestaurantTags(@AuthenticationPrincipal User user) {
        var restaurant = restaurantService.getRestaurantById(user.getRestaurant().getId());
        return restaurant.getTags();
    }

    @GetMapping
    public String owner(Model model,@AuthenticationPrincipal User user) {
        model.addAttribute("editOwner", new EditOwnerDTO( user.getRestaurant().getName(), user.getRestaurant().getDescription()));
        return "profile/owner";
    }
//CONDITIONALS
    @PostMapping("/editOwner")
    public String editOwner(@Valid @ModelAttribute(name = "editOwner") EditOwnerDTO editOwnerDTO,
                            BindingResult bindingResult,
                            Model model,
                            @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("editOwner", editOwnerDTO);
            return "profile/owner";
        }
        if (restaurantService.RestaurantExistsByName(editOwnerDTO.getRestaurantName())
        && !user.getRestaurant().getName().equals(editOwnerDTO.getRestaurantName())) {
            model.addAttribute("editOwner", editOwnerDTO);
            model.addAttribute("errorMessage", "Restaurant already exists");
            return "profile/owner";
        }
        restaurantService.editRestaurant(editOwnerDTO,user.getRestaurant());
        restaurantService.updateTagsToRestaurant(user.getRestaurant(),editOwnerDTO.getTags());
        return "redirect:/profile";
    }
}
