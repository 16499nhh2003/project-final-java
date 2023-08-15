package com.project.spring.dto;

import com.project.spring.model.Product;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO{
    private Long id;
    private String name;
}
