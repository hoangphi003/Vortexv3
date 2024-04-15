package com.techvortex.vortex.service;

import java.util.List;
import com.techvortex.vortex.entity.Category;

public interface CategoryService {

    public List<Category> findAll(); // in ra lưu vào danh sách

    public Category findById(Integer CategoryId); // tìm kiếm theo id

    public Category create(Category category); // thêm danh sách

    public Category update(Category category); // sửa danh sách

    public void delete(Category category);

    public boolean existsByNameIgnoreCase(String categoryName);

    public List<Category> findAllDesc();
}
