package com.project.spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name not empty!")
    private String name;
    //    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    @Email(message = "Please provide a valid e-mail")
    /*@NotEmpty(message = "Please provide an e-mail")*/
    private String email;

    private String phoneNumber;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Cart> carts;

    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    private String photo;
    private String address;
    private boolean gender;
    @Column
    private String resetToken;

    @Column(columnDefinition = "boolean default true")
    private boolean isEnable;

    @Column(columnDefinition = "boolean default false")
    private boolean isHide;
}
