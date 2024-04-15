package com.techvortex.vortex.serviceimplement;
import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import com.techvortex.vortex.entity.Category;
import com.techvortex.vortex.repository.CategoryDAO;
import com.techvortex.vortex.service.CategoryService;

import javax.transaction.Transactional;

@Service
public class CategoryImpl implements CategoryService {
    
    @Autowired
    CategoryDAO categoryDAO;

    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    @Transactional
    public Category findById(Integer CategoryId) {
        return categoryDAO.findById(CategoryId).get();
    }

    @Override
    public Category create(Category category) {
        return categoryDAO.save(category);
    }

    @Override
    public Category update(Category category) {
        return categoryDAO.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryDAO.delete(category);
    }

    @Override
    public boolean existsByNameIgnoreCase(String categoryName) {
        return categoryDAO.existsByNameIgnoreCase(categoryName) > 0;
    }
    
    @Override
    public List<Category> findAllDesc() {
         return categoryDAO.findAllByCategoryId();
    }
}
