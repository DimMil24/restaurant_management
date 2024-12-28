package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.services.RestaurantService;
import com.dimitris.restaurant_management.services.TagService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final TagService tagService;
    private final RestaurantService restaurantService;

    public ProfileController(TagService tagService, RestaurantService restaurantService) {
        this.tagService = tagService;
        this.restaurantService = restaurantService;
    }


    @GetMapping
    public String owner(Model model,@AuthenticationPrincipal User user) {
        var restaurant = restaurantService.getRestaurantById(user.getRestaurant().getId());
        model.addAttribute("user", user);
        model.addAttribute("restaurantTags", restaurant.getTags());
        model.addAttribute("tags", tagService.findAll());
        return "profile/owner";
    }
}
