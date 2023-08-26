package com.project.spring.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class RevenueCategoryDTO {
    private String nameCategory;
    private Integer quantity;
    private BigDecimal total;
    private Double priceMin;
    private Double priceMax;
    private BigDecimal gpa;
}
