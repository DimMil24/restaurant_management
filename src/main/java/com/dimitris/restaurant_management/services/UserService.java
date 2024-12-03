package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.entities.UserRoles;
import com.dimitris.restaurant_management.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(String username, String password, UserRoles role) {
        User user = new User(username, password, role,null);
        return userRepository.save(user);
    }

    @Transactional
    public void associateUserToRestaurant(User user, Restaurant restaurant) {
        user.setRestaurant(restaurant);
        userRepository.save(user);
    }
}
