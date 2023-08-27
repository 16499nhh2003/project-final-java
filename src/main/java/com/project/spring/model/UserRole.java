package com.project.spring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users_roles")
public class UserRole {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public void setUser(AppUser user) {
        this.user = user;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Constructors, getters, setters...
}
