package com.project.spring.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RevenueProductDTO {
    private String name;
    private int quantity;
    private BigDecimal total;
    private double price;
    private BigDecimal gpa;
}
