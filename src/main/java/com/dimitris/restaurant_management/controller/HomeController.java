package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.services.RestaurantService;
import com.dimitris.restaurant_management.services.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@Controller
public class HomeController {

    private final RestaurantService restaurantService;
    private final TagService tagService;

    public HomeController(RestaurantService restaurantService, TagService tagService) {
        this.restaurantService = restaurantService;
        this.tagService = tagService;
    }

    @GetMapping("/")
    public String index(@RequestParam(name = "filter", required = false) String[] filter, Model model) {
        if (filter != null && filter.length > 0) {
            model.addAttribute("filter", filter);
        } else {
            model.addAttribute("filter", new String[0]);
        }
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("restaurants", restaurantService.getRestaurantsByFilter(filter));
        return "hello";
    }
}
