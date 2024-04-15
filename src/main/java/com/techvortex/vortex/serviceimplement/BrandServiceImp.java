package com.techvortex.vortex.serviceimplement;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Brand;
import com.techvortex.vortex.entity.Discount;
import com.techvortex.vortex.entity.Material;
import com.techvortex.vortex.repository.BrandDao;
import com.techvortex.vortex.service.BrandService;

@Service
public class BrandServiceImp implements BrandService {
    @Autowired
    BrandDao cdao;

    @Override
    public List<Brand> findAll() {
        // Lấy danh sách tất cả các danh mục từ dao và trả về
        return cdao.findAll();
    }

    @Override
    public Page<Brand> findAll(Pageable pageable) {
        // Lấy một trang danh sách danh mục dựa trên phân trang từ dao và trả về
        return cdao.findAll(pageable);
    }

    @Override
    @Transactional
    public Brand findById(Integer brandId) {  
        return cdao.findById(brandId).get();
    }
    

    @Override
    public Brand create(Brand brand) {
        // Tạo mới một danh mục thông qua dao và trả về
        return cdao.save(brand);
    }

    @Override
    public Brand update(Brand brand) {
        // Cập nhật thông tin của một danh mục thông qua dao và trả về
        return cdao.save(brand);
    }

    @Override
    public void delete(Brand brand) {
        // Xóa một danh mục thông qua dao
        cdao.delete(brand);
    }

    // @Override
    // public Page<Brand> search(String name, Pageable pageable) {
    //     // Tìm kiếm danh mục dựa trên tên với hỗ trợ phân trang từ dao và trả về
    //     return cdao.findByBrandNameContaining(name, pageable);
    // }

    
}