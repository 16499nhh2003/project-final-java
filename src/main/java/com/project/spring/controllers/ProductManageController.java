package com.project.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;

@Controller
public class ProductManageController {
	private boolean showNewProductRow = false;
	@GetMapping("/product_m")
	public String getProducts(Model model) {
        model.addAttribute("showNewProductRow", showNewProductRow);
        return "product_m";
    }
	@PostMapping("/addProduct")
	public String addProduct(@RequestParam("new_product_name") String name, @RequestParam("new_product_img") String image) {
	        
	    showNewProductRow = false;

        return "redirect:/product_m";
    }
	@GetMapping("/toggleNewProductRow")
    public String toggleNewProductRow() {
       
        showNewProductRow = true;
        return "redirect:/product_m";
    }
}
