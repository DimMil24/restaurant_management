package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.entities.requests.RegisterOwnerRequest;
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


    public RegisterService(RestaurantService restaurantService, PasswordEncoder passwordEncoder,
                           UserService userService, RoleService roleService) {
        this.restaurantService = restaurantService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Transactional
    public int registerRestaurantOwner(RegisterOwnerRequest registerOwnerRequest) {
        if (roleService.roleMissing("ROLE_ADMIN")) {
            roleService.createRole("ROLE_ADMIN");
        }
        if (roleService.roleMissing("ROLE_OWNER")) {
            roleService.createRole("ROLE_OWNER");
        }
        if(userService.checkUserExists(registerOwnerRequest.username())) {
            return 1;
        }
        if (restaurantService.findRestaurantByName(registerOwnerRequest.restaurantName())) {
            return 2;
        }
        User user = userService.createUser(registerOwnerRequest.username(),
                passwordEncoder.encode(registerOwnerRequest.password()));
        roleService.addRolesToUser(List.of("ROLE_ADMIN", "ROLE_OWNER"), user);
        Restaurant restaurant = restaurantService.addRestaurant(registerOwnerRequest.restaurantName(),
                registerOwnerRequest.restaurantDesc());
        userService.associateUserToRestaurant(user, restaurant);
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
            roleService.addRolesToUser(List.of("ROLE_USER"), user);
            return true;
        }
        return false;
    }
}
