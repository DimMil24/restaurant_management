package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.Tag;
import com.dimitris.restaurant_management.entities.requests.RegisterOwnerDTO;
import com.dimitris.restaurant_management.entities.requests.RegisterUserDTO;
import com.dimitris.restaurant_management.services.RegisterService;
import com.dimitris.restaurant_management.services.RestaurantService;
import com.dimitris.restaurant_management.services.TagService;
import com.dimitris.restaurant_management.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final RegisterService registerService;
    private final TagService tagService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    public RegisterController(RegisterService registerService, TagService tagService, UserService userService, RestaurantService restaurantService) {
        this.registerService = registerService;
        this.tagService = tagService;
        this.userService = userService;
        this.restaurantService = restaurantService;
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
    public String user(Model model) {
        model.addAttribute("user", new RegisterUserDTO());
        return "register/user";
    }

    @PostMapping("/createOwner")
    public String newRestaurantUser (@Valid @ModelAttribute(name = "owner") RegisterOwnerDTO registerOwnerDTO,
                                     BindingResult bindingResult,
                                     Model model,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("owner",registerOwnerDTO);
            return "register/owner";
        }
        if (userService.checkUserExists(registerOwnerDTO.getUsername())) {
            model.addAttribute("owner",registerOwnerDTO);
            model.addAttribute("errorMessage", "This user already exists");
            return "register/owner";
        }
        if (restaurantService.RestaurantExistsByName(registerOwnerDTO.getRestaurantName())) {
            model.addAttribute("owner",registerOwnerDTO);
            model.addAttribute("errorMessage", "This restaurant already exists");
            return "register/owner";
        }
        registerService.registerRestaurantOwner(registerOwnerDTO);
        registerService.loginOwner(registerOwnerDTO,request,response);
        return "redirect:/";
    }

    @PostMapping("/createUser")
    public String newUser (@Valid @ModelAttribute(name = "user") RegisterUserDTO registerUserDTO,
                           BindingResult bindingResult,
                           Model model,
                           HttpServletResponse response,
                           HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("owner",registerUserDTO);
            return "register/user";
        }
        if (userService.checkUserExists(registerUserDTO.getUsername())) {
            model.addAttribute("owner",registerUserDTO);
            model.addAttribute("errorMessage", "This user already exists");
            return "register/user";
        }
        registerService.registerUser(registerUserDTO);
        registerService.loginUser(registerUserDTO, request, response);
        return "redirect:user?userDuplicate=true";
    }
}
