package com.techvortex.vortex.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.techvortex.vortex.entity.Brand;
import com.techvortex.vortex.entity.Discount;

public interface DiscountService {
    // Trả về danh sách tất cả các danh mục
    List<Discount> findAll();

    // Trả về một trang danh sách các danh mục dựa trên phân trang
    Page<Discount> findAll(Pageable pageable);

    // Tìm kiếm danh mục theo ID và trả về kết quả dưới dạng Optional
    Optional<Discount> findById(Integer categoryId);

    // Tạo mới một danh mục và trả về nó
    Discount create(Discount categories);

    // Cập nhật thông tin của một danh mục và trả về nó
    Discount update(Discount categories);

    // Xóa một danh mục khỏi hệ thống
    void delete(Discount categories);

    // Tìm kiếm các danh mục dựa trên tên và phân trang kết quả
    // Page<Discount> search(String name, Pageable pageable);
}
