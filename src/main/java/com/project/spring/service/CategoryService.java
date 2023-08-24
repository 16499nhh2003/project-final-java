package com.project.spring.service;

import com.project.spring.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    void deleteAllCategory();

    List<Category> findAllCategory();

}
