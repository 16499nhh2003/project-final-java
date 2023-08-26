package com.project.spring.service.impl;

import com.project.spring.model.Role;
import com.project.spring.repositories.RoleRepository;
import com.project.spring.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
<<<<<<< Updated upstream
import java.util.Set;
import java.util.stream.Collectors;
=======
>>>>>>> Stashed changes

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(int id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.orElse(null);
    }
<<<<<<< Updated upstream

    @Override
    public Set<Role> getRolesByIds(List<Integer> roleIds) {
        return roleIds.stream()
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
=======
}
>>>>>>> Stashed changes
