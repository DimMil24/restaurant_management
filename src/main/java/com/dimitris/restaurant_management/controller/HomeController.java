package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.*;
import com.dimitris.restaurant_management.services.RestaurantService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@Controller
public class HomeController {

    private final RestaurantService restaurantService;

    public HomeController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
//        if (session.getAttribute("username") == null) {
//            session.setAttribute("username", user.));
//        }
        if (authentication !=null && authentication.isAuthenticated()) {
            User user = (User )authentication.getPrincipal();
            model.addAttribute("message",user.getId() + " " + user.getUsername());
        }
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        return "hello";
    }
}
