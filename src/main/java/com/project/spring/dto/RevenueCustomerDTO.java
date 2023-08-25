package com.project.spring.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RevenueCustomerDTO {
    private Long id;
    private String name;
    private int quantity;
    private BigDecimal total;
    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private BigDecimal gpa;
}
