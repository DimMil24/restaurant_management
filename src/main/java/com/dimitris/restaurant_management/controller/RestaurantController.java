package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.entities.requests.NewOrderRequest;
import com.dimitris.restaurant_management.services.CustomerOrderService;
import com.dimitris.restaurant_management.services.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return "restaurant/index";
    }
}
