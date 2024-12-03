package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.entities.UserRoles;
import com.dimitris.restaurant_management.entities.requests.RegisterOwnerRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
    private final RestaurantService restaurantService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    public RegisterService(RestaurantService restaurantService, PasswordEncoder passwordEncoder,
                           UserService userService) {
        this.restaurantService = restaurantService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Transactional
    public void registerRestaurantOwner(RegisterOwnerRequest registerOwnerRequest) {
        User user = userService.createUser(registerOwnerRequest.username(),
                        passwordEncoder.encode(registerOwnerRequest.password()),
                        UserRoles.ROLE_OWNER);

        Restaurant restaurant = restaurantService.addRestaurant(registerOwnerRequest.restaurantName(),
                                                                registerOwnerRequest.restaurantDesc(),
                                                                user);
        userService.associateUserToRestaurant(user, restaurant);
    }
}
