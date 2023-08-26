package com.project.spring.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RevenueMonthDTO {
    private int month;
    private int year;
    private BigDecimal total;
}
