package com.techvortex.vortex.service;

import java.util.List;
import com.techvortex.vortex.entity.Review;

public interface ReviewService {
   
    public List<Review> findAll(); // in ra lưu vào danh sách

    public Review findById(Integer ReviewId); // tìm kiếm theo id 

   public Review create(Review review ); // thêm danh sách

   public Review update(Review review ); // sửa danh sách

   public void delete(Review review );
}
