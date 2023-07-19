package com.project.spring.controller;

import com.project.spring.model.Product;
import com.project.spring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/product")
public class ProductController {
//    @Autowired
//    private ProductRepository productRepository;
    @GetMapping("")
    public String index() {
        return "product";
    }
//    @GetMapping("/all")
//    public Iterable<Product> getAllProducts(){
//        return this.productRepository.findAll();
//    }
}
