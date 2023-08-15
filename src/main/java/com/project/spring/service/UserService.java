package com.project.spring.service;

import com.project.spring.model.AppUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    Optional<AppUser> findUserById(Long id);

    Iterable<AppUser> getAllUsers();
    AppUser getUserById(Long id);
    void saveUser(AppUser user);
    void updateUser(Long id, AppUser user);
    void deleteUser(Long id);
}
