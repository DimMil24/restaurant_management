package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.requests.RegisterOwnerRequest;
import com.dimitris.restaurant_management.services.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping
    public String index() {
        return "/register/index";
    }

    @PostMapping("/createOwner")
    public String newRestaurantUser (RegisterOwnerRequest registerOwnerRequest) {
        registerService.registerRestaurantOwner(registerOwnerRequest);
        return "redirect:/";
    }

}
