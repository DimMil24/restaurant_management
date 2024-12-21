package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.services.CategoryService;
import com.dimitris.restaurant_management.services.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final CategoryService categoryService;

    public RestaurantController(RestaurantService restaurantService, CategoryService categoryService) {
        this.restaurantService = restaurantService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{restaurantId}")
    public String restaurant(@PathVariable UUID restaurantId, Model model) {
        var categories = categoryService.findAllCategoriesByRestaurantPublic(restaurantId);
        model.addAttribute("restaurant",restaurantService.getRestaurantById(restaurantId));
        model.addAttribute("categories",categories);
        return "restaurant/index";
    }
}
