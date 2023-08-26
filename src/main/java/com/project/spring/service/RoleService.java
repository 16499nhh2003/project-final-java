package com.project.spring.service;
<<<<<<< Updated upstream

import com.project.spring.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleById(int id);

    Set<Role> getRolesByIds(List<Integer> roleIds);
=======
import com.project.spring.model.Role;
import com.project.spring.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
>>>>>>> Stashed changes
}
