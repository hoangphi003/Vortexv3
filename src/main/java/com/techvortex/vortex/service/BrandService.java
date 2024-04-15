package com.techvortex.vortex.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.techvortex.vortex.entity.Brand;
import com.techvortex.vortex.entity.Material;

public interface BrandService {

     // Trả về danh sách tất cả các danh mục
    List<Brand> findAll();

    // Trả về một trang danh sách các danh mục dựa trên phân trang
    Page<Brand> findAll(Pageable pageable);

    // Tìm kiếm danh mục theo ID và trả về kết quả dưới dạng Optional
    // Optional<Brand> findById(Integer categoryId);

    public Brand findById(Integer brandId); // tìm kiếm theo id
    // Tạo mới một danh mục và trả về nó
    Brand create(Brand brand);

    // Cập nhật thông tin của một danh mục và trả về nó
    Brand update(Brand brand);

    // Xóa một danh mục khỏi hệ thống
    void delete(Brand brand);

    // Tìm kiếm các danh mục dựa trên tên và phân trang kết quả
    // Page<Brand> search(String name, Pageable pageable);



}
