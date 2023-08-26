package com.project.spring.service;

import com.project.spring.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleById(int id);

    Set<Role> getRolesByIds(List<Integer> roleIds);
}
