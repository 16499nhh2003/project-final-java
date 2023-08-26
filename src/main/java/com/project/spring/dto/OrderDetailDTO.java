package com.project.spring.dto;

import com.project.spring.model.Order;
import com.project.spring.model.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailDTO {
    private Long idProduct;
    private Long orderId;
    private String nameProduct;
    private double priceProduct;
    private int quantity;
    private String url;
    private String color;
}
