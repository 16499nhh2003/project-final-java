package com.project.spring.controller;

import com.project.spring.model.AppUser;
import com.project.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        Iterable<AppUser> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userList";
    }
    @GetMapping("/users/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "newUserForm";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute AppUser user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        AppUser user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "editUserForm";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute AppUser user) {
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
