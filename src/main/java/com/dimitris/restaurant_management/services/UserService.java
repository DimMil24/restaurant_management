package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.entities.Role;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Transactional
    public User createUser(String username, String password) {
        User user = new User(username, password,null, null);
        return userRepository.save(user);
    }

    @Transactional
    public void associateUserToRestaurant(User user, Restaurant restaurant) {
        user.setRestaurant(restaurant);
        userRepository.save(user);
    }

    public boolean checkUserExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public void assignUserToRoles(List<String> roles, User user) {
        List<Role> roleList = new ArrayList<>();
        for (String name : roles) {
            if (roleService.getRole(name).isPresent()) {
                roleList.add(roleService.getRole(name).get());
            }
        }
        user.setRoleList(roleList);
        userRepository.save(user);
    }

    public Optional<User> findUserByName(String username) {
        return userRepository.findByUsername(username);
    }

}
