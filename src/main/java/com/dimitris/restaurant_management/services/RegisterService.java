package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.entities.requests.RegisterOwnerRequest;
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
    public void registerRestaurantOwner(RegisterOwnerRequest registerOwnerRequest) {
        User user = userService.createUser(registerOwnerRequest.username(),
                        passwordEncoder.encode(registerOwnerRequest.password()));

        roleService.addRolesToUser(List.of("ROLE_ADMIN","ROLE_OWNER"), user);

        Restaurant restaurant = restaurantService.addRestaurant(registerOwnerRequest.restaurantName(),
                                                                registerOwnerRequest.restaurantDesc());
        userService.associateUserToRestaurant(user, restaurant);
    }
}
