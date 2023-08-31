package com.project.spring.controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.project.spring.model.Role;
import com.project.spring.model.AppUser;
import com.project.spring.repositories.UserRepository;


@Controller
public class UserSignUpController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


	@Autowired
    public UserSignUpController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

	@GetMapping("/register")
    public String getSignUpForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";
    }
	
	@PostMapping("/register")
	public String signUpAcc(@ModelAttribute("user") AppUser userInf, Model model) {
		Optional<AppUser> emails=userRepository.findByEmail(userInf.getEmail());
		if (emails.isPresent()) {
	        model.addAttribute("emailExists", true);
	        return "register";
	    }
		Optional<AppUser> username=userRepository.findByUsername(userInf.getUsername());
		if (username.isPresent()) {
	        model.addAttribute("nameExists", true);
	        return "register";
	    }
		
		String encodedPassword = passwordEncoder.encode(userInf.getPassword());
	    userInf.setPassword(encodedPassword);

        List<Role> roles = new ArrayList<>();
        roles.add(new Role("admin")); 
        userInf.setRoles(roles);
        

//		if(avatarFile.isEmpty()) {
//			userInf.setPhoto("assets/avatar/default-avatar.png");
//		}else {
//			try {
//				Path upload = Paths.get("avatar/");
//	            InputStream input = avatarFile.getInputStream(); 
//	            Files.copy(input, upload.resolve(avatarFile.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
//	            userInf.setPhoto(avatarFile.getOriginalFilename().toLowerCase());
//			} catch (IOException e) {
//	            e.printStackTrace();
//	        }
//		}
//		
		userRepository.save(userInf);
	    return "redirect:/login";
	}

}
