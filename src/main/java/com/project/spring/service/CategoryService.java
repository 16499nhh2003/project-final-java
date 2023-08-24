package com.project.spring.service;

import com.project.spring.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {
    void deleteAllCategory();

    List<Category> findAllCategory();

    Optional<Category> findCategoryByName(String name);

}
