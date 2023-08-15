package com.project.spring.dto;

import com.project.spring.model.Product;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDTO{
    private ProductDTO productDTO;
    private Integer quantity;
}
