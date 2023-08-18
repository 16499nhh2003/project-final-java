package com.project.spring.controllers;
import com.project.spring.model.Product;
import com.project.spring.service.ProductManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/product-management")
public class ProductManagementController {
    private final ProductManagementService productManagementService;

    @Autowired
    public ProductManagementController(ProductManagementService productManagementService) {
        this.productManagementService = productManagementService;
    }

    @GetMapping("/list")
    public String listProducts(Model model) {
        model.addAttribute("products", productManagementService.getAllProducts());
        return "list";
    }

    @GetMapping("/view/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productManagementService.getProductById(id).orElse(null));
        return "view";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "new";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product) {
        productManagementService.saveProduct(product);
        return "redirect:/product-management/list";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productManagementService.getProductById(id).orElse(null));
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        productManagementService.saveProduct(product);
        return "redirect:/product-management/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productManagementService.deleteProduct(id);
        return "redirect:/product-management/list";
    }
    @GetMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> searchResults = productManagementService.searchProducts(keyword);
        model.addAttribute("products", searchResults);
        return "list";
    }
}
