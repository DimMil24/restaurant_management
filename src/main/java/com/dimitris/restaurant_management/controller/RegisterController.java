package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.Tag;
import com.dimitris.restaurant_management.entities.requests.RegisterOwnerDTO;
import com.dimitris.restaurant_management.entities.requests.RegisterUserRequest;
import com.dimitris.restaurant_management.services.RegisterService;
import com.dimitris.restaurant_management.services.TagService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final RegisterService registerService;
    private final TagService tagService;
    private final AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    public RegisterController(RegisterService registerService, TagService tagService, AuthenticationManager authenticationManager) {
        this.registerService = registerService;
        this.tagService = tagService;
        this.authenticationManager = authenticationManager;
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
    public String newRestaurantUser (@Valid RegisterOwnerDTO registerOwnerDTO,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
      int registerResult = registerService.registerRestaurantOwner(registerOwnerDTO);
        if (registerResult==1) {
            return "redirect:owner?userDuplicate=true";
        } else if (registerResult==2) {
            return "redirect:owner?restaurantDuplicate=true";
        } else {
            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                    registerOwnerDTO.getUsername(), registerOwnerDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            return "redirect:/";
        }
    }

    @PostMapping("/createUser")
    public String newUser (@Valid RegisterUserRequest registerUserRequest,
                           HttpServletResponse response,
                           HttpServletRequest request) {
        boolean userNotExists = registerService.registerUser(registerUserRequest);
        if (userNotExists) {
            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                    registerUserRequest.username(), registerUserRequest.password());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            return "redirect:/";
        }
        return "redirect:user?userDuplicate=true";
    }
}
