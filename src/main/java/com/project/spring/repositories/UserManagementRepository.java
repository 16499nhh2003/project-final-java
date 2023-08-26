package com.project.spring.repositories;

import com.project.spring.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserManagementRepository extends JpaRepository<AppUser, Long> {
    List<AppUser> findByNameContainingOrUsernameContainingOrEmailContaining(String keyword, String keyword1, String keyword2);
    // Các phương thức tùy chỉnh nếu cần
}