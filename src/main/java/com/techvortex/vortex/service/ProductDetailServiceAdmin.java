package com.techvortex.vortex.service;

import com.techvortex.vortex.entity.Color;
import com.techvortex.vortex.entity.Material;
import com.techvortex.vortex.entity.Product;
import com.techvortex.vortex.entity.ProductDetail;
import com.techvortex.vortex.repository.ProductDetailDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public interface ProductDetailServiceAdmin {

    public ProductDetail findById(Integer ProductDetailId);

    // ProductDetail findByProduct(Product product);

    public List<ProductDetail> findAll(); // in ra lưu vào danh sách

    public ProductDetail create(ProductDetail productDetail); // thêm danh sách

    public ProductDetail update(ProductDetail productDetail); // sửa danh sách

    public void delete(ProductDetail productDetail);

    boolean existsById(Integer id);

    int getTotalRemainingProducts();

    void updateInventoryQuantity(Integer productId, Integer quantity);

}