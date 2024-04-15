package com.techvortex.vortex.service;
import java.util.List;

import com.techvortex.vortex.entity.Product;

public interface ProductService {

    public List<Product> findAll(); // in ra lưu vào danh sách

	 public Product findById(Integer ProductId); // tìm kiếm theo id 

	public Product create(Product product); // thêm danh sách

	public Product update(Product product); // sửa danh sách

	public void delete(Product product);

	public boolean existsByNameIgnoreCase(String productName); // bắt lỗi trùng tên
 
}
