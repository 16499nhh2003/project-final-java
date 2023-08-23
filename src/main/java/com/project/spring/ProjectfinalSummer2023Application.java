package com.project.spring;

import com.project.spring.service.EmailService;
import com.project.spring.service.impl.CategoryServiceImpl;
import com.project.spring.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.mail.MessagingException;

@SpringBootApplication
public class ProjectfinalSummer2023Application implements CommandLineRunner {
//    @Autowired
//    ProductServiceImpl productService;
//    @Autowired
//    CategoryServiceImpl categoryService;
    public static void main(String[] args) {
        SpringApplication.run(ProjectfinalSummer2023Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        productService.deleteAll();
//        categoryService.deleteAllCategory();
    }
}