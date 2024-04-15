package com.techvortex.vortex.serviceimplement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Brand;
import com.techvortex.vortex.entity.Discount;
import com.techvortex.vortex.repository.BrandDao;
import com.techvortex.vortex.repository.DiscountDao;
import com.techvortex.vortex.service.BrandService;
import com.techvortex.vortex.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService{
    @Autowired
     DiscountDao cdao;

     @Override
     public List<Discount> findAll() {
         // Lấy danh sách tất cả các danh mục từ dao và trả về
         return cdao.findAll();
     }
 
     @Override
     public Page<Discount> findAll(Pageable pageable) {
         // Lấy một trang danh sách danh mục dựa trên phân trang từ dao và trả về
         return cdao.findAll(pageable);
     }
 
     @Override
     public Optional<Discount> findById(Integer categoryId) {
         // Tìm kiếm một danh mục theo ID từ dao và trả về kết quả dưới dạng Optional
         return cdao.findById(categoryId);
     }
 
     @Override
     public Discount create(Discount categories) {
         // Tạo mới một danh mục thông qua dao và trả về
         return cdao.save(categories);
     }
 
     @Override
     public Discount update(Discount categories) {
         // Cập nhật thông tin của một danh mục thông qua dao và trả về
         return cdao.save(categories);
     }
 
     @Override
     public void delete(Discount categories) {
         // Xóa một danh mục thông qua dao
         cdao.delete(categories);
     }
 
    //  @Override
    //  public Page<Discount> search(String name, Pageable pageable) {
    //      // Tìm kiếm danh mục dựa trên tên với hỗ trợ phân trang từ dao và trả về
    //      return cdao.findByDiscountNameContaining(name, pageable);
    //  }

    
}
