package com.project.spring.controllers;

import com.project.spring.model.AppUser;
import com.project.spring.repositories.UserRepository;
import com.project.spring.service.UserService;
import com.project.spring.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.FeatureDescriptor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Controller
//@RequestMapping("/account")
public class UserController {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/uploads/users/";

    @GetMapping("/account")
    public String profileUser(Model model) {
        AppUser appUser = this.userRepository.getUserByUsername(this.userDetailsService.getCurrentUserId());
        model.addAttribute("isLogin", appUser.getName());
        model.addAttribute("userRequest", appUser);
        return "profile";
    }
    @PostMapping("/account")
    public String updateInfo(@Valid @ModelAttribute("userRequest") AppUser userRequest,
                             BindingResult bindingResult,
                             @RequestParam("image") MultipartFile file,
                             Model model, RedirectAttributes redirectAttributes
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            model.addAttribute("errors", errorMessages);
            return "profile";
        }
        AppUser user = this.userRepository.getUserByUsername(this.userDetailsService.getCurrentUserId());
        if (!file.isEmpty()) {
            StringBuilder fileNames = new StringBuilder();
            String fileName = file.getOriginalFilename();
            Path uploadPath = Path.of(UPLOAD_DIRECTORY, fileName);
            Files.write(uploadPath, file.getBytes());
            user.setPhoto(fileName);
        }
        BeanUtils.copyProperties(userRequest, user, getNullPropertyNames(userRequest));
        this.userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "success");
        return "redirect:/account";
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        Iterable<AppUser> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user/userList";
    }
    @GetMapping("/users/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "admin/user/newUserForm";
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
        return "admin/user/editUserForm";
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
