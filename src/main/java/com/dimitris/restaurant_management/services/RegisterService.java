package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.entities.requests.RegisterOwnerDTO;
import com.dimitris.restaurant_management.entities.requests.RegisterUserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegisterService {
    private final RestaurantService restaurantService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();


    public RegisterService(RestaurantService restaurantService, PasswordEncoder passwordEncoder,
                           UserService userService, RoleService roleService, AuthenticationManager authenticationManager) {
        this.restaurantService = restaurantService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
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
        if (restaurantService.RestaurantExistsByName(registerOwnerDTO.getRestaurantName())) {
            return 2;
        }
        User user = userService.createUser(registerOwnerDTO.getUsername(),
                passwordEncoder.encode(registerOwnerDTO.getPassword()));
        userService.assignUserToRoles(List.of("ROLE_ADMIN", "ROLE_OWNER"), user);
        Restaurant restaurant = restaurantService.addRestaurant(registerOwnerDTO.getRestaurantName(),
                registerOwnerDTO.getRestaurantDesc());
        userService.associateUserToRestaurant(user, restaurant);
        restaurantService.associateRestaurantToTags(restaurant, registerOwnerDTO.getTags());
        return 0;
    }

    @Transactional
    public boolean registerUser(RegisterUserDTO registerUserDTO) {
        if (roleService.roleMissing("ROLE_USER")) {
            roleService.createRole("ROLE_USER");
        }
        if (!userService.checkUserExists(registerUserDTO.getUsername())) {
            User user = userService.createUser(registerUserDTO.getUsername(),
                    passwordEncoder.encode(registerUserDTO.getPassword()));
            userService.assignUserToRoles(List.of("ROLE_USER"), user);
            return true;
        }
        return false;
    }

    public void loginUser(RegisterUserDTO registerUserDTO,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                registerUserDTO.getUsername(), registerUserDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);
    }

    public void loginOwner(RegisterOwnerDTO registerOwnerDTO,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                registerOwnerDTO.getUsername(), registerOwnerDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);
    }
}
