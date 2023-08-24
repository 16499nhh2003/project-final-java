package com.project.spring.service.impl;

import com.project.spring.model.Category;
import com.project.spring.repositories.CategoryRepository;
import com.project.spring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public void deleteAllCategory() {
        categoryRepository.deleteAll();
    }

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }
}
