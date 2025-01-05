package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.Tag;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.entities.requests.EditOwnerDTO;
import com.dimitris.restaurant_management.services.RestaurantService;
import com.dimitris.restaurant_management.services.TagService;
import com.dimitris.restaurant_management.services.UserService;
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
    private final UserService userService;

    public ProfileController(TagService tagService, RestaurantService restaurantService, UserService userService) {
        this.tagService = tagService;
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @ModelAttribute("tags")
    public List<Tag> getTags() {
        return tagService.findAll();
    }

    @GetMapping
    public String owner(Model model,@AuthenticationPrincipal User user) {
        var restaurant = restaurantService.getRestaurantById(user.getRestaurant().getId());
        model.addAttribute("editOwner",
                new EditOwnerDTO( restaurant.getName(), restaurant.getDescription()));
        model.addAttribute("user", user);
        model.addAttribute("restaurantTags", restaurant.getTags());
        return "profile/owner";
    }
//CONDITIONALS
    @PostMapping("/editOwner")
    public String editOwner(@Valid @ModelAttribute(name = "editOwner") EditOwnerDTO editOwnerDTO,
                            BindingResult bindingResult,
                            Model model,
                            @AuthenticationPrincipal User user) {
        var restaurant = restaurantService.getRestaurantById(user.getRestaurant().getId());
        if (bindingResult.hasErrors()) {
            model.addAttribute("editOwner", editOwnerDTO);
            model.addAttribute("user", user);
            model.addAttribute("restaurantTags", restaurant.getTags());
            return "profile/owner";
        }
        restaurantService.editRestaurant(editOwnerDTO,user.getRestaurant());
        return "redirect:/profile";
    }
}
