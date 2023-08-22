package com.project.spring.controllers.admin;

import com.project.spring.model.Product;
import com.project.spring.service.impl.ProductServiceImpl;
import com.project.spring.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


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

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/upload/";

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        /*if (!multipartFile.isEmpty()) {
            try {
                String originalFileName = multipartFile.getOriginalFilename();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String uniqueFileName = generateUniqueFileName(fileExtension);
                Path targetPath = Path.of(UPLOAD_DIRECTORY, uniqueFileName);
                Files.copy(imageFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                product.setOriginalPicture(uniqueFileName);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }*/
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        product.setOriginalPicture(fileName);
        product.setViewCount(0L);
        Product savedProduct = this.productService.addOrUpdate(product);
        String uploadDir = "./upload/products/";
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/admin/products/list";
    }

    private String generateUniqueFileName(String fileExtension) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = currentTime.format(formatter);
        return "upload_" + formattedDateTime + fileExtension;
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id).orElse(null));
        return "admin/products/edit";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        productService.addOrUpdate(product);
        return "redirect:/admin/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/admin/products/list";
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
