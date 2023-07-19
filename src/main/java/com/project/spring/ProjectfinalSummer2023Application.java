package com.project.spring;

import com.project.spring.model.Product;
import com.project.spring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ProjectfinalSummer2023Application  implements CommandLineRunner {
    @Autowired
    ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProjectfinalSummer2023Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Product> products = (List<Product>) this.productRepository.findAll();
        products.forEach(System.out::println);
    }
}