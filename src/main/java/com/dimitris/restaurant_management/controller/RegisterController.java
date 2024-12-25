package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.Tag;
import com.dimitris.restaurant_management.entities.requests.RegisterOwnerDTO;
import com.dimitris.restaurant_management.entities.requests.RegisterUserRequest;
import com.dimitris.restaurant_management.services.RegisterService;
import com.dimitris.restaurant_management.services.TagService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final RegisterService registerService;
    private final TagService tagService;

    public RegisterController(RegisterService registerService, TagService tagService) {
        this.registerService = registerService;
        this.tagService = tagService;
    }

    @ModelAttribute("tags")
    public List<Tag> getTags() {
        return tagService.findAll();
    }

    @GetMapping("/owner")
    public String owner(Model model) {
        model.addAttribute("owner", new RegisterOwnerDTO());
        return "register/owner";
    }

    @GetMapping("/user")
    public String user() {
        return "register/user";
    }

    @PostMapping("/createOwner")
    public String newRestaurantUser (@Valid RegisterOwnerDTO registerOwnerDTO) {
      int registerResult = registerService.registerRestaurantOwner(registerOwnerDTO);
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
