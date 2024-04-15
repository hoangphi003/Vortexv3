package com.techvortex.vortex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvortex.vortex.entity.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

    @Query("SELECT COUNT(c) FROM Category c WHERE LOWER(c.CategoryName) = LOWER(:categoryName)")
    int existsByNameIgnoreCase(@Param("categoryName") String categoryName);

    @Query("select c from Category c order by c.CategoryId desc")
    List<Category> findAllByCategoryId();

}
