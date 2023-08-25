package com.project.spring.repositories;

import com.project.spring.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    @Query("SELECT u FROM AppUser u WHERE u.username = :username or  u.email = : username")
    AppUser getUserByUsername(@Param("username") String username);

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByResetToken(String resetToken);

}
