package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.services.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{restaurantId}")
    public String restaurant(@PathVariable UUID restaurantId, Model model) {
        model.addAttribute("restaurant",restaurantService.getRestaurantById(restaurantId));
        model.addAttribute("products",restaurantService.getProductsByRestaurant(restaurantId));
        return "restaurant";
    }
}
