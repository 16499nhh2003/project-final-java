package com.project.spring;

import com.project.spring.model.Category;
import com.project.spring.model.Manufacture;
import com.project.spring.service.EmailService;
import com.project.spring.service.impl.CategoryServiceImpl;
import com.project.spring.service.impl.ManufactureServiceImpl;
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
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    ManufactureServiceImpl manufactureService;
    public static void main(String[] args) {
        SpringApplication.run(ProjectfinalSummer2023Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        // detele all
//        productService.deleteAll();
//        categoryService.deleteAllCategory();
//        manufactureService.deleteALl();

//        // add category
//        categoryService.addOrUpdate(new Category("Electrical Device", "https://electricaldevice.com"));
//        categoryService.addOrUpdate(new Category("Mobile Phone", "https://mobilephone.com"));
//        categoryService.addOrUpdate(new Category("Tablet", "https://teblet.com"));
//        categoryService.addOrUpdate(new Category("PC", "https://pc.com"));
//
//        // add manufacture
//        manufactureService.addOrUpdate(new Manufacture("Samsung"));
//        manufactureService.addOrUpdate(new Manufacture("Apple"));
//        manufactureService.addOrUpdate(new Manufacture("Xiaomi"));
//        manufactureService.addOrUpdate(new Manufacture("Realme"));

    }
}