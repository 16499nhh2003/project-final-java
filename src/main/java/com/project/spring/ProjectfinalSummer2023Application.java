package com.project.spring;

import com.project.spring.model.AppUser;
import com.project.spring.model.Role;
import com.project.spring.service.EmailService;
import com.project.spring.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ProjectfinalSummer2023Application implements CommandLineRunner {
//    @Autowired
//    UserServiceImpl userService;

    public static void main(String[] args) {
        SpringApplication.run(ProjectfinalSummer2023Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        AppUser appUser = new AppUser();
//        appUser.setName("quang");
//        appUser.setUsername("userquang");
//        appUser.setPassword("123");
//        appUser.setEmail("quang@gmail.com");
//        appUser.setAddress("TPHCM");
//        appUser.setPhoneNumber("01234567890");
//        appUser.setPhoto("None");
//        appUser.setGender(true);
//        appUser.setResetToken("H");
//        List<Role> roles = new ArrayList<Role>();
//        Role role = new Role();
//        role.setName("admin");
//        roles.add(role);
//        appUser.setRoles(roles);
//        userService.save(appUser);

    }
}