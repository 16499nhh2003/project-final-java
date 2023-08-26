package com.project.spring.service;

import com.project.spring.model.AppUser;
import com.project.spring.model.Role;
import com.project.spring.repositories.RoleRepository;
import com.project.spring.repositories.UserManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserManagementService {
    private final UserManagementRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public UserManagementService(UserManagementRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<AppUser> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public AppUser saveUser(AppUser user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<AppUser> searchUsers(String keyword) {
        return userRepository.findByNameContainingOrUsernameContainingOrEmailContaining(keyword, keyword, keyword);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<Role> getRolesByNames(List<String> roleNames) {
        return roleRepository.findByNameIn(roleNames);
    }
}