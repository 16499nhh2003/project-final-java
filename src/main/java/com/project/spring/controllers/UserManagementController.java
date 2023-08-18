package com.project.spring.controllers;

import com.project.spring.model.AppUser;
import com.project.spring.service.UserManagementService;
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

    @Autowired
    public UserManagementController(UserManagementService userService) {
        this.userService = userService;
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
        return "user-form";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute AppUser user) {
        userService.saveUser(user);
        return "redirect:/users/";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        Optional<AppUser> user = userService.getUserById(id);
        user.ifPresent(appUser -> model.addAttribute("user", appUser));
        return "user-form";
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
