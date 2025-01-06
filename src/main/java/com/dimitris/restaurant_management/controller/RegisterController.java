package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.Tag;
import com.dimitris.restaurant_management.entities.requests.RegisterOwnerDTO;
import com.dimitris.restaurant_management.entities.requests.RegisterUserDTO;
import com.dimitris.restaurant_management.services.RegisterService;
import com.dimitris.restaurant_management.services.TagService;
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
        int registerResult = registerService.registerRestaurantOwner(registerOwnerDTO);
        if (registerResult==1) {
            return "redirect:owner?userDuplicate=true";
        } else if (registerResult==2) {
            return "redirect:owner?restaurantDuplicate=true";
        } else {
            registerService.loginOwner(registerOwnerDTO,request,response);
            return "redirect:/";
        }
    }
//TODO: Change duplicate logic same as edit Owner
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
        boolean userNotExists = registerService.registerUser(registerUserDTO);
        if (userNotExists) {
            registerService.loginUser(registerUserDTO, request, response);
            return "redirect:/";
        }
        return "redirect:user?userDuplicate=true";
    }
}
