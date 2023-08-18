package com.project.spring.controllers.admin;

import com.project.spring.model.Product;
import com.project.spring.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin/products")
public class ProductManagementController {

    @Autowired
    ProductServiceImpl productService;

    @GetMapping("/list")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products/list";
    }

    @GetMapping("/view/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id).orElse(null));
        return "admin/products/view";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/products/new";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product) {
        productService.addOrUpdate(product);
        return "redirect:/admin/list";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id).orElse(null));
        return "admin/products/edit";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        productService.addOrUpdate(product);
        return "redirect:/admin/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/admin/list";
    }

    @PostMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        if (keyword.contains("=")) {
            String key = keyword.strip().split("=")[0];
            String value = keyword.strip().split("=")[1];

            // id = ...
            if (key.equalsIgnoreCase("id")) {
                try {
                    Long id = Long.parseLong(value);
                    model.addAttribute("products", productService.getProductById(id));
                    return "admin/products/list";
                } catch (NumberFormatException e) {
                    model.addAttribute("errorMessage", "id value must be a number");
                    model.addAttribute("products", productService.getAllProducts());
                    return "admin/products/list";
                }

                // name = ...
            } else if (key.equalsIgnoreCase("name")) {
                model.addAttribute("products", productService.findProductByNameContaining(value));
                return "admin/products/list";

                // price = ...
            } else if (key.equalsIgnoreCase("price")) {
                if (value.contains("-")) {
                    try {
                        Double lowestValue = Double.parseDouble(value.strip().split("-")[0]);
                        Double highestValue = Double.parseDouble(value.strip().split("-")[1]);
                        model.addAttribute("products", productService.findProductByPriceBetween(lowestValue, highestValue));
                        return "admin/products/list";
                    } catch (NumberFormatException e) {
                        model.addAttribute("errorMessage", "price value must be a number");
                        model.addAttribute("products", productService.getAllProducts());
                        return "admin/products/list";
                    }
                } else {
                    try {
                        Double price = Double.parseDouble(value);
                        model.addAttribute("products", productService.findProductByPrice(price));
                        return "admin/products/list";
                    } catch (NumberFormatException e) {
                        model.addAttribute("errorMessage", "price value must be a number");
                        model.addAttribute("products", productService.getAllProducts());
                        return "admin/products/list";
                    }
                }

                // color = ...
            } else if (key.equalsIgnoreCase("color")) {
                model.addAttribute("products", productService.findProductByColor(value));
                return "admin/products/list";

                // description = ...
            } else if (key.equalsIgnoreCase("description")) {
                model.addAttribute("products", productService.findProductByDescriptionContaining(value));
                return "admin/products/list";

                // information = ...
            } else if (key.equalsIgnoreCase("information")) {
                model.addAttribute("products", productService.findProductByInformationContaining(value));
                return "admin/products/list";

                // size = ...
            } else if (key.equalsIgnoreCase("size")) {
                try {
                    int size = Integer.parseInt(value);
                    model.addAttribute("products", productService.findProductBySize(size));
                    return "admin/products/list";
                } catch (NumberFormatException e) {
                    model.addAttribute("errorMessage", "size value must be a number");
                    model.addAttribute("products", productService.getAllProducts());
                    return "admin/products/list";
                }

                // view count = ....
            } else if (key.equalsIgnoreCase("view count")) {
                if (value.contains("-")) {
                    try {
                        Long lowestValue = Long.parseLong(value.strip().split("-")[0]);
                        Long highestValue = Long.parseLong(value.strip().split("-")[1]);
                        model.addAttribute("products", productService.findProductByViewCountBetween(lowestValue, highestValue));
                        return "admin/products/list";
                    } catch (NumberFormatException e) {
                        model.addAttribute("errorMessage", "price value must be a number");
                        model.addAttribute("products", productService.getAllProducts());
                        return "admin/products/list";
                    }
                } else {
                    try {
                        Long viewCount = Long.parseLong(value);
                        model.addAttribute("products", productService.findProductByViewCount(viewCount));
                        return "admin/products/list";
                    } catch (NumberFormatException e) {
                        model.addAttribute("errorMessage", "price value must be a number");
                        model.addAttribute("products", productService.getAllProducts());
                        return "admin/products/list";
                    }
                }

            } else {
                model.addAttribute("errorMessage", "Not found");
                model.addAttribute("products", productService.getAllProducts());
                return "admin/products/list";
            }
        } else {
            model.addAttribute("errorMessage", "value must containing '=' ");
            model.addAttribute("products", productService.getAllProducts());
            return "admin/products/list";
        }
    }
}
