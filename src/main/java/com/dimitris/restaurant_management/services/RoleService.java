package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Role;
import com.dimitris.restaurant_management.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public boolean roleMissing(String roleName) {
        return roleRepository.findByName(roleName).isEmpty();
    }

    @Transactional
    public void createRole(String role) {
        roleRepository.save(new Role(null,role));
    }

    public Optional<Role> getRole(String roleName) {
        return roleRepository.findByName(roleName);
    }

}
