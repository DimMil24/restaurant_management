package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.requests.RegisterOwnerRequest;
import com.dimitris.restaurant_management.entities.requests.RegisterUserRequest;
import com.dimitris.restaurant_management.services.RegisterService;
import jakarta.validation.Valid;
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

    @GetMapping("/owner")
    public String owner() {
        return "register/owner";
    }

    @GetMapping("/user")
    public String user() {
        return "register/user";
    }

    @PostMapping("/createOwner")
    public String newRestaurantUser (@Valid RegisterOwnerRequest registerOwnerRequest) {
        int registerResult = registerService.registerRestaurantOwner(registerOwnerRequest);
        if (registerResult==1) {
            return "redirect:owner?userDuplicate=true";
        } else if (registerResult==2) {
            return "redirect:owner?restaurantDuplicate=true";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/createUser")
    public String newUser (@Valid RegisterUserRequest registerUserRequest) {
        boolean userNotExists = registerService.registerUser(registerUserRequest);
        if (userNotExists) {
            return "redirect:/";
        }
        return "redirect:user?userDuplicate=true";
    }

}
