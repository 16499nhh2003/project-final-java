package com.project.spring.controllers;

import com.project.spring.model.AppUser;
import com.project.spring.model.Role;
import com.project.spring.service.UserManagementService;
import com.project.spring.service.RoleService; // Import thêm service cho Role
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserManagementController {
    private final UserManagementService userService;
    private final RoleService roleService; // Inject RoleService

    @Autowired
    public UserManagementController(UserManagementService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService; // Inject RoleService
    }

    @GetMapping("/")
    public String listUsers(Model model) {
        List<AppUser> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new AppUser());
<<<<<<< Updated upstream:src/main/java/com/project/spring/controllers/UserManagementController.java
<<<<<<< Updated upstream:src/main/java/com/project/spring/controllers/UserManagementController.java
        return "user-form";
=======
=======
>>>>>>> Stashed changes:src/main/java/com/project/spring/controllers/admin/UserManagementController.java
        model.addAttribute("allRoles", roleService.getAllRoles()); // Truyền danh sách vai trò
        return "admin/user/user-form";
>>>>>>> Stashed changes:src/main/java/com/project/spring/controllers/admin/UserManagementController.java
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute AppUser user) {
        userService.saveUser(user);
        return "redirect:/users/";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        Optional<AppUser> user = userService.getUserById(id);
<<<<<<< Updated upstream:src/main/java/com/project/spring/controllers/UserManagementController.java
<<<<<<< Updated upstream:src/main/java/com/project/spring/controllers/UserManagementController.java
        user.ifPresent(appUser -> model.addAttribute("user", appUser));
        return "user-form";
=======
=======
>>>>>>> Stashed changes:src/main/java/com/project/spring/controllers/admin/UserManagementController.java
        user.ifPresent(appUser -> {
            model.addAttribute("user", appUser);
            model.addAttribute("allRoles", roleService.getAllRoles()); // Truyền danh sách vai trò
        });
        return "admin/user/user-form";
>>>>>>> Stashed changes:src/main/java/com/project/spring/controllers/admin/UserManagementController.java
    }

    @PostMapping("/edit/{id}")
    public String editUser(@ModelAttribute AppUser user) {
        userService.saveUser(user);
        return "redirect:/users/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users/";
    }
}
