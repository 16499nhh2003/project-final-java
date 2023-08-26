package com.project.spring.controllers.admin;

import com.project.spring.model.AppUser;
import com.project.spring.model.Role;
<<<<<<< Updated upstream
=======
import com.project.spring.service.RoleService;
>>>>>>> Stashed changes
import com.project.spring.service.UserManagementService;
import com.project.spring.service.RoleService; // Import thêm service cho Role
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin/users")
public class UserManagementController {
    private final UserManagementService userService;
<<<<<<< Updated upstream
    private final RoleService roleService; // Inject RoleService

    @Autowired
    public UserManagementController(UserManagementService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService; // Inject RoleService
=======
    private final RoleService roleService;



    @Autowired
    public UserManagementController(UserManagementService userService,RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
>>>>>>> Stashed changes
    }

    @GetMapping("/")
    public String listUsers(Model model) {
        List<AppUser> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user/user-list";
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new AppUser());
<<<<<<< Updated upstream
        model.addAttribute("allRoles", roleService.getAllRoles()); // Truyền danh sách vai trò
=======
        model.addAttribute("allRoles", roleService.getAllRoles()); // Truyền danh sách roles vào Model
>>>>>>> Stashed changes
        return "admin/user/user-form";
    }

    @PostMapping("/add")
<<<<<<< Updated upstream
    public String addUser(@ModelAttribute AppUser user, @RequestParam("roles") List<Integer> roleIds) {
        Set<Role> roles = roleService.getRolesByIds(roleIds);
        userService.saveUserWithRoles(user, roles);
=======
    public String addUser(@ModelAttribute AppUser user, @RequestParam List<Role> roles) {
        if (!userService.existsByEmail(user.getEmail())) {
            user.setRoles(roles);
            userService.saveUser(user);
        } else {
            // Xử lý khi email đã tồn tại
        }
>>>>>>> Stashed changes
        return "redirect:/admin/users/";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        Optional<AppUser> user = userService.getUserById(id);
        user.ifPresent(appUser -> {
            model.addAttribute("user", appUser);
            model.addAttribute("allRoles", roleService.getAllRoles()); // Truyền danh sách vai trò
        });
        return "admin/user/user-form";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@ModelAttribute AppUser user) {
        userService.saveUser(user);
        return "redirect:/admin/users/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users/";
    }
<<<<<<< Updated upstream
}
=======
    @GetMapping("/search")
    public String searchUsers(@RequestParam("keyword") String keyword, Model model) {
        List<AppUser> searchResults = userService.searchUsers(keyword);
        model.addAttribute("users", searchResults);
        return "admin/user/user-list";
    }
}
>>>>>>> Stashed changes
