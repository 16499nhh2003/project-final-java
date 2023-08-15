package com.project.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductByCategoryDTO {
    private ProductDTO productDTO;
    private Integer quantity;
}
