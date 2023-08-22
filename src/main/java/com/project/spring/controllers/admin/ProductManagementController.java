package com.project.spring.controllers.admin;

import com.project.spring.model.Product;
import com.project.spring.service.impl.ProductServiceImpl;
import com.project.spring.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public String listProducts(Model model,
                               @RequestParam(name = "currentPage", defaultValue = "0") String currentPage,
                               @RequestParam(name = "numberElementInOnePage", defaultValue = "10") String numberElementInOnePage,
                               @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                               @RequestParam(name = "orderField", defaultValue = "acs") String orderField) {

        Sort.Direction direction = orderField.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order = new Sort.Order(direction, sortBy);
        Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(numberElementInOnePage), Sort.by(order));

        model.addAttribute("products", productService.pageFindAllProduct(pageable).getContent());
        model.addAttribute("totalPages", productService.pageFindAllProduct(pageable).getTotalPages());
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("numberElementInOnePage", Integer.parseInt(numberElementInOnePage));
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("orderField", orderField);
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
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam("image") MultipartFile multipartFile,
                              Model model,
                              @RequestParam(name = "currentPage", defaultValue = "0") String currentPage,
                              @RequestParam(name = "numberElementInOnePage", defaultValue = "10") String numberElementInOnePage,
                              @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                              @RequestParam(name = "orderField", defaultValue = "acs") String orderField) throws IOException {
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

        if (multipartFile == null) {
            productService.addOrUpdate(product);
            Sort.Direction direction = orderField.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort.Order order = new Sort.Order(direction, sortBy);
            Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(numberElementInOnePage), Sort.by(order));

            model.addAttribute("products", productService.pageFindAllProduct(pageable).getContent());
            model.addAttribute("totalPages", productService.pageFindAllProduct(pageable).getTotalPages());
            model.addAttribute("currentPage", Integer.parseInt(currentPage));
            model.addAttribute("numberElementInOnePage", Integer.parseInt(numberElementInOnePage));
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("orderField", orderField);
            return "/admin/products/list";
        }
        else {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            product.setOriginalPicture(fileName);
            product.setViewCount(0L);
            Product savedProduct = this.productService.addOrUpdate(product);
            String uploadDir = "./upload/products/";
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            Sort.Direction direction = orderField.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort.Order order = new Sort.Order(direction, sortBy);
            Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(numberElementInOnePage), Sort.by(order));

            model.addAttribute("products", productService.pageFindAllProduct(pageable).getContent());
            model.addAttribute("totalPages", productService.pageFindAllProduct(pageable).getTotalPages());
            model.addAttribute("currentPage", Integer.parseInt(currentPage));
            model.addAttribute("numberElementInOnePage", Integer.parseInt(numberElementInOnePage));
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("orderField", orderField);
            return "/admin/products/list";
        }

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

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product product,
                                Model model,
                                @RequestParam(name = "currentPage", defaultValue = "0") String currentPage,
                                @RequestParam(name = "numberElementInOnePage", defaultValue = "10") String numberElementInOnePage,
                                @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                @RequestParam(name = "orderField", defaultValue = "acs") String orderField) {

        productService.addOrUpdate(product);
        Sort.Direction direction = orderField.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order = new Sort.Order(direction, sortBy);
        Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(numberElementInOnePage), Sort.by(order));

        model.addAttribute("products", productService.pageFindAllProduct(pageable).getContent());
        model.addAttribute("totalPages", productService.pageFindAllProduct(pageable).getTotalPages());
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("numberElementInOnePage", Integer.parseInt(numberElementInOnePage));
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("orderField", orderField);
        return "redirect:/admin/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id,
                                Model model,
                                @RequestParam(name = "currentPage", defaultValue = "0") String currentPage,
                                @RequestParam(name = "numberElementInOnePage", defaultValue = "10") String numberElementInOnePage,
                                @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                @RequestParam(name = "orderField", defaultValue = "acs") String orderField) {

        productService.deleteProductById(id);
        Sort.Direction direction = orderField.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order = new Sort.Order(direction, sortBy);
        Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(numberElementInOnePage), Sort.by(order));

        model.addAttribute("products", productService.pageFindAllProduct(pageable).getContent());
        model.addAttribute("totalPages", productService.pageFindAllProduct(pageable).getTotalPages());
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("numberElementInOnePage", Integer.parseInt(numberElementInOnePage));
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("orderField", orderField);
        return "redirect:/admin/products/list";
    }

    @PostMapping ("/search")
    public String searchProducts(@RequestParam("keyword") String keyword,
                                 Model model,
                                 @RequestParam(name = "currentPage", defaultValue = "0") String currentPage,
                                 @RequestParam(name = "numberElementInOnePage", defaultValue = "10") String numberElementInOnePage,
                                 @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                 @RequestParam(name = "orderField", defaultValue = "acs") String orderField) {

        if (keyword.contains("=")){
            String key = keyword.replace(" ","").strip().split("=")[0].strip();
            String value = keyword.strip().split("=")[1].strip();

            // id = ...
            if (key.equalsIgnoreCase("id")) {
                try {
                    Long id = Long.parseLong(value);
                    Sort.Direction direction = orderField.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                    Sort.Order order = new Sort.Order(direction, sortBy);
                    Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(numberElementInOnePage), Sort.by(order));

                    model.addAttribute("products", productService.pageFindProductById(id, pageable));
                    model.addAttribute("totalPages", productService.pageFindAllProduct(pageable).getTotalPages());
                    model.addAttribute("currentPage", Integer.parseInt(currentPage));
                    model.addAttribute("numberElementInOnePage", Integer.parseInt(numberElementInOnePage));
                    model.addAttribute("sortBy", sortBy);
                    model.addAttribute("orderField", orderField);
                    return "admin/products/list";
                } catch (NumberFormatException e) {
                    model.addAttribute("errorMessage", "id value must be a number");
                    model.addAttribute("products", null);
                    return "admin/products/list";
                }

                // name = ...
            } else if (key.equalsIgnoreCase("name")) {
                Sort.Direction direction = orderField.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                Sort.Order order = new Sort.Order(direction, sortBy);
                Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(numberElementInOnePage), Sort.by(order));

                model.addAttribute("products", productService.pageFindProductByNameContaining(value, pageable));
                model.addAttribute("totalPages", productService.pageFindAllProduct(pageable).getTotalPages());
                model.addAttribute("currentPage", Integer.parseInt(currentPage));
                model.addAttribute("numberElementInOnePage", Integer.parseInt(numberElementInOnePage));
                model.addAttribute("sortBy", sortBy);
                model.addAttribute("orderField", orderField);
                return "admin/products/list";

                // price = ...
            } else if (key.equalsIgnoreCase("price")) {
                if (value.contains("-")) {
                    try {
                        Double minPrice = Double.parseDouble(value.strip().split("-")[0]);
                        Double maxPrice = Double.parseDouble(value.strip().split("-")[1]);
                        Sort.Direction direction = orderField.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                        Sort.Order order = new Sort.Order(direction, sortBy);
                        Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(numberElementInOnePage), Sort.by(order));

                        model.addAttribute("products", productService.pageFindProductByPriceBetween(minPrice, maxPrice, pageable));
                        model.addAttribute("totalPages", productService.pageFindAllProduct(pageable).getTotalPages());
                        model.addAttribute("currentPage", Integer.parseInt(currentPage));
                        model.addAttribute("numberElementInOnePage", Integer.parseInt(numberElementInOnePage));
                        model.addAttribute("sortBy", sortBy);
                        model.addAttribute("orderField", orderField);
                        return "admin/products/list";
                    } catch (NumberFormatException e) {
                        model.addAttribute("errorMessage", "price value must be a number");
                        model.addAttribute("products", null);
                        return "admin/products/list";
                    }
                } else {
                    try {
                        Double price = Double.parseDouble(value);
                        Sort.Direction direction = orderField.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                        Sort.Order order = new Sort.Order(direction, sortBy);
                        Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(numberElementInOnePage), Sort.by(order));

                        model.addAttribute("products", productService.pageFindProductByPrice(price, pageable));
                        model.addAttribute("totalPages", productService.pageFindAllProduct(pageable).getTotalPages());
                        model.addAttribute("currentPage", Integer.parseInt(currentPage));
                        model.addAttribute("numberElementInOnePage", Integer.parseInt(numberElementInOnePage));
                        model.addAttribute("sortBy", sortBy);
                        model.addAttribute("orderField", orderField);
                        return "admin/products/list";
                    } catch (NumberFormatException e) {
                        model.addAttribute("errorMessage", "price value must be a number");
                        model.addAttribute("products", null);
                        return "admin/products/list";
                    }
                }

                // color = ...
            } else if (key.equalsIgnoreCase("color")) {
                Sort.Direction direction = orderField.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                Sort.Order order = new Sort.Order(direction, sortBy);
                Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(numberElementInOnePage), Sort.by(order));

                model.addAttribute("products", productService.pageFindProductByColor(value, pageable));
                model.addAttribute("totalPages", productService.pageFindAllProduct(pageable).getTotalPages());
                model.addAttribute("currentPage", Integer.parseInt(currentPage));
                model.addAttribute("numberElementInOnePage", Integer.parseInt(numberElementInOnePage));
                model.addAttribute("sortBy", sortBy);
                model.addAttribute("orderField", orderField);
                return "admin/products/list";

                // description = ...
            } else if (key.equalsIgnoreCase("description")) {
                Sort.Direction direction = orderField.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                Sort.Order order = new Sort.Order(direction, sortBy);
                Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(numberElementInOnePage), Sort.by(order));

                model.addAttribute("products", productService.pageFindProductByDescriptionContaining(value, pageable));
                model.addAttribute("totalPages", productService.pageFindAllProduct(pageable).getTotalPages());
                model.addAttribute("currentPage", Integer.parseInt(currentPage));
                model.addAttribute("numberElementInOnePage", Integer.parseInt(numberElementInOnePage));
                model.addAttribute("sortBy", sortBy);
                model.addAttribute("orderField", orderField);
                return "admin/products/list";

                // information = ...
            } else if (key.equalsIgnoreCase("information")) {
                Sort.Direction direction = orderField.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                Sort.Order order = new Sort.Order(direction, sortBy);
                Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(numberElementInOnePage), Sort.by(order));

                model.addAttribute("products", productService.pageFindProductByInformationContaining(value, pageable));
                model.addAttribute("totalPages", productService.pageFindAllProduct(pageable).getTotalPages());
                model.addAttribute("currentPage", Integer.parseInt(currentPage));
                model.addAttribute("numberElementInOnePage", Integer.parseInt(numberElementInOnePage));
                model.addAttribute("sortBy", sortBy);
                model.addAttribute("orderField", orderField);
                return "admin/products/list";

                // size = ...
            } else if (key.equalsIgnoreCase("size")) {
                try {
                    int size = Integer.parseInt(value);
                    Sort.Direction direction = orderField.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                    Sort.Order order = new Sort.Order(direction, sortBy);
                    Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(numberElementInOnePage), Sort.by(order));

                    model.addAttribute("products", productService.pageFindProductBySize(size, pageable));
                    model.addAttribute("totalPages", productService.pageFindAllProduct(pageable).getTotalPages());
                    model.addAttribute("currentPage", Integer.parseInt(currentPage));
                    model.addAttribute("numberElementInOnePage", Integer.parseInt(numberElementInOnePage));
                    model.addAttribute("sortBy", sortBy);
                    model.addAttribute("orderField", orderField);
                    return "admin/products/list";
                } catch (NumberFormatException e) {
                    model.addAttribute("errorMessage", "size value must be a number");
                    model.addAttribute("products", null);
                    return "admin/products/list";
                }

                // view count = ....
            } else if (key.equalsIgnoreCase("viewcount")) {
                if (value.contains("-")) {
                    try {
                        Long lowestValue = Long.parseLong(value.strip().split("-")[0]);
                        Long highestValue = Long.parseLong(value.strip().split("-")[1]);
                        Sort.Direction direction = orderField.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                        Sort.Order order = new Sort.Order(direction, sortBy);
                        Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(numberElementInOnePage), Sort.by(order));

                        model.addAttribute("products", productService.pageFindProductByViewCountBetween(lowestValue, highestValue, pageable));
                        model.addAttribute("totalPages", productService.pageFindAllProduct(pageable).getTotalPages());
                        model.addAttribute("currentPage", Integer.parseInt(currentPage));
                        model.addAttribute("numberElementInOnePage", Integer.parseInt(numberElementInOnePage));
                        model.addAttribute("sortBy", sortBy);
                        model.addAttribute("orderField", orderField);
                        return "admin/products/list";
                    } catch (NumberFormatException e) {
                        model.addAttribute("errorMessage", "price value must be a number");
                        model.addAttribute("products", null);
                        return "admin/products/list";
                    }
                } else {
                    try {
                        Long viewCount = Long.parseLong(value);
                        Sort.Direction direction = orderField.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                        Sort.Order order = new Sort.Order(direction, sortBy);
                        Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(numberElementInOnePage), Sort.by(order));

                        model.addAttribute("products", productService.pageFindProductByViewCount(viewCount, pageable));
                        model.addAttribute("totalPages", productService.pageFindAllProduct(pageable).getTotalPages());
                        model.addAttribute("currentPage", Integer.parseInt(currentPage));
                        model.addAttribute("numberElementInOnePage", Integer.parseInt(numberElementInOnePage));
                        model.addAttribute("sortBy", sortBy);
                        model.addAttribute("orderField", orderField);
                        return "admin/products/list";
                    } catch (NumberFormatException e) {
                        model.addAttribute("errorMessage", "price value must be a number");
                        model.addAttribute("products", null);
                        return "admin/products/list";
                    }
                }

            } else {
                model.addAttribute("errorMessage", "please enter correct keyword");
                model.addAttribute("products", null);
                return "admin/products/list";
            }
        } else {
            model.addAttribute("errorMessage", "value must containing '=' ");
            model.addAttribute("products", null);
            return "admin/products/list";
        }
    }
}
