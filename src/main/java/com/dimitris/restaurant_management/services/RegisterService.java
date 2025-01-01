package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.entities.requests.RegisterOwnerDTO;
import com.dimitris.restaurant_management.entities.requests.RegisterUserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegisterService {
    private final RestaurantService restaurantService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final TagService tagService;


    public RegisterService(RestaurantService restaurantService, PasswordEncoder passwordEncoder,
                           UserService userService, RoleService roleService, TagService tagService) {
        this.restaurantService = restaurantService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
        this.tagService = tagService;
    }


    @Transactional
    public int registerRestaurantOwner(RegisterOwnerDTO registerOwnerDTO) {
        if (roleService.roleMissing("ROLE_ADMIN")) {
            roleService.createRole("ROLE_ADMIN");
        }
        if (roleService.roleMissing("ROLE_OWNER")) {
            roleService.createRole("ROLE_OWNER");
        }
        if(userService.checkUserExists(registerOwnerDTO.getUsername())) {
            return 1;
        }
        if (restaurantService.findRestaurantByName(registerOwnerDTO.getRestaurantName())) {
            return 2;
        }
        User user = userService.createUser(registerOwnerDTO.getUsername(),
                passwordEncoder.encode(registerOwnerDTO.getPassword()));
        userService.assignUserToRoles(List.of("ROLE_ADMIN", "ROLE_OWNER"), user);
        Restaurant restaurant = restaurantService.addRestaurant(registerOwnerDTO.getRestaurantName(),
                registerOwnerDTO.getRestaurantDesc());
        userService.associateUserToRestaurant(user, restaurant);
        tagService.associateTagsToRestaurant(restaurant,registerOwnerDTO.getTags());
        return 0;
    }

    @Transactional
    public boolean registerUser(RegisterUserRequest registerUserRequest) {
        if (roleService.roleMissing("ROLE_USER")) {
            roleService.createRole("ROLE_USER");
        }
        if (!userService.checkUserExists(registerUserRequest.username())) {
            User user = userService.createUser(registerUserRequest.username(),
                    passwordEncoder.encode(registerUserRequest.password()));
            userService.assignUserToRoles(List.of("ROLE_USER"), user);
            return true;
        }
        return false;
    }
}
