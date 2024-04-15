package com.techvortex.vortex.serviceimplement;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Color;
import com.techvortex.vortex.entity.Material;
import com.techvortex.vortex.entity.Product;
import com.techvortex.vortex.entity.ProductDetail;
import com.techvortex.vortex.repository.ProductDetailDao;
import com.techvortex.vortex.service.ProductDetailService;
import com.techvortex.vortex.service.ProductDetailServiceAdmin;

@Service
public class ProductDetailServiceImpAdmin implements ProductDetailServiceAdmin {

    @Autowired
    ProductDetailDao productDetailDao;

    @Override
public ProductDetail findById(Integer id) {
    Optional<ProductDetail> optionalProductDetail = productDetailDao.findById(id);

    if (optionalProductDetail.isPresent()) {
        return optionalProductDetail.get();
    } else {
        // Xử lý trường hợp không tìm thấy
        throw new EntityNotFoundException("Không tìm thấy ProductDetail với ID: " + id);
    }
}

    @Override
    public List<ProductDetail> findAll() {

        return productDetailDao.findAll();
    }
    

    @Override
    public boolean existsById(Integer id) {
        return productDetailDao.existsById(id);
    }


    @Override
    public ProductDetail create(ProductDetail productDetail) {
        return productDetailDao.save(productDetail);
    }


    @Override
    public ProductDetail update(ProductDetail productDetail) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }


    @Override
    public void delete(ProductDetail productDetail) {
        // TODO Auto-generated method stub
        productDetailDao.delete(productDetail);
    }

    // @Override
    // public ProductDetail findByProduct(Product product) {
        
    //     return productDetailDao.findByProduct(product);
    // }

    @Override
    public int getTotalRemainingProducts() {
        List<ProductDetail> allProductDetails = productDetailDao.findAll();
        if (allProductDetails == null || allProductDetails.isEmpty()) {
            return 0; // Trả về 0 nếu không có sản phẩm nào trong cơ sở dữ liệu
        }
        return allProductDetails.stream()
            .filter(Objects::nonNull) // Loại bỏ các sản phẩm null (nếu có)
            .mapToInt(product -> product.getInventoryQuantity() != null ? product.getInventoryQuantity() : 0) // Đảm bảo không tính số lượng null
            .sum();
    }

    @Override
    public void updateInventoryQuantity(Integer productId, Integer quantity) {
        ProductDetail productDetail = findById(productId);
        if (productDetail != null) {
            Integer currentQuantity = productDetail.getInventoryQuantity();
            if (currentQuantity != null && currentQuantity >= quantity) {
                productDetail.setInventoryQuantity(currentQuantity - quantity);
                update(productDetail);
            } else {
                throw new RuntimeException("Số lượng tồn kho không đủ.");
            }
        } else {
            throw new EntityNotFoundException("Không tìm thấy sản phẩm với ID: " + productId);
        }
    }
  
}
