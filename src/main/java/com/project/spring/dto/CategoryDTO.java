package com.project.spring.dto;

import com.project.spring.model.Manufacture;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private Integer quantity;
    private String url;
    private List<ManufactureDTO> manufactures;
}