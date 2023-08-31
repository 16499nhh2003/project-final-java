package com.project.spring.controllers.admin;

import com.project.spring.model.AppUser;
import com.project.spring.model.Role;
import com.project.spring.model.UserRole;
import com.project.spring.repositories.RoleRepository;
import com.project.spring.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
public class UserManagementController {
    private final UserManagementService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserManagementController(UserManagementService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository; // Khởi tạo roleRepository
    }

    @GetMapping("/")
    public String listUsers(Model model) {
        List<AppUser> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "/admin/user/user-list";
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new AppUser());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "/admin/user/user-form";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute AppUser user, @RequestParam(name = "selectedRoles", required = false) List<Long> selectedRoles, Model model) {
        if (userService.isEmailExists(user.getEmail())) {
            model.addAttribute("emailExistsError", "Email already exists");
            model.addAttribute("user", user);
            model.addAttribute("allRoles", roleRepository.findAll());
            return "/admin/user/user-form";
        }

        if (selectedRoles != null) {
            for (Long roleId : selectedRoles) {
                Role role = roleRepository.findById(roleId).orElse(null);
                if (role != null) {
                    UserRole userRole = new UserRole();
                    userRole.setUser(user);
                    userRole.setRole(role);
//                    user.getRoles().add(userRole);
                }
            }
        }

        userService.saveUser(user);
        return "redirect:/admin/users/";
    }


    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        Optional<AppUser> user = userService.getUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("allRoles", roleRepository.findAll());
            return "/admin/user/user-form";
        }
        return "redirect:/admin/users/";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@ModelAttribute AppUser user, Model model) {
        // Lấy thông tin người dùng cũ để kiểm tra email
        Optional<AppUser> oldUserOptional = userService.getUserById(user.getId());

        if (oldUserOptional.isPresent()) {
            AppUser oldUser = oldUserOptional.get();

            // Kiểm tra xem email đã tồn tại chưa (ngoại trừ người dùng đang chỉnh sửa)
            if (!oldUser.getEmail().equals(user.getEmail()) && userService.isEmailExists(user.getEmail())) {
                model.addAttribute("emailExistsError", "Email already exists");
                model.addAttribute("user", user); // Để hiển thị lại thông tin người dùng trên form
                return "/admin/user/user-form";
            }
        }

        userService.saveUser(user);
        return "redirect:/admin/users/";
    }



    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users/";
    }
    @GetMapping("/search")
    public String searchUsers(@RequestParam("keyword") String keyword, Model model) {
        List<AppUser> searchResults = userService.searchUsers(keyword);
        model.addAttribute("users", searchResults);
        return "/admin/user/user-list";
    }}