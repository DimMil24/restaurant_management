package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.entities.UserRoles;
import com.dimitris.restaurant_management.entities.requests.RegisterOwnerRequest;
import com.dimitris.restaurant_management.repositories.RestaurantRepository;
import com.dimitris.restaurant_management.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;


    public RegisterService(UserRepository userRepository, RestaurantRepository restaurantRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerRestaurantOwner(RegisterOwnerRequest registerOwnerRequest) {
        User user = new User(registerOwnerRequest.getUsername(),
                            passwordEncoder.encode(registerOwnerRequest.getPassword()),
                            UserRoles.ROLE_OWNER,
                            null);
        userRepository.save(user);

        Restaurant restaurant = new Restaurant(registerOwnerRequest.getRestaurantName(),
                                                true,
                                                registerOwnerRequest.getRestaurantDesc(),
                                                user);

        restaurantRepository.save(restaurant);
        user.setRestaurant(restaurant);
        userRepository.save(user);
    }
}
