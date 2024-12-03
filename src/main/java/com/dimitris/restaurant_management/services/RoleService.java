package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Role;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void createRole(String role) {
        roleRepository.save(new Role(null,role));
    }

    @Transactional
    public void addRolesToUser(List<String> roles, User user) {
        for (String name : roles) {
            Role role = roleRepository.findByName(name).orElseThrow(() ->
                    new RuntimeException("Role Not Found"));
            role.getUserList().add(user);
            roleRepository.save(role);
        }
    }

}
