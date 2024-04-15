package com.techvortex.vortex.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.ProductDetail;

public interface ProductDetailService {

    ProductDetail findById(Integer id);

    Page<ProductDetail> findAll(Pageable pageable);

    Page<ProductDetail> findAllByOrderByPriceDesc(Pageable pageable);

    Page<ProductDetail> findAllByOrderByPriceAsc(Pageable pageable);

    Page<ProductDetail> findAllByOrderByNameAsc(Pageable pageable);

    Page<ProductDetail> findAllByPriceLessThanOrderByPriceAsc(Pageable pageable);

    Page<ProductDetail> findAllByPriceLessThanOrderByPriceAsc1(Pageable pageable);

    List<ProductDetail> findAllByPriceInRangeOrderByPriceAsc(Double minPrice, Double maxPrice, Pageable pageable);

    Page<ProductDetail> findByBrands(List<String> brands, Pageable pageable);

    Page<ProductDetail> findByCategorys(List<String> categorys, Pageable pageable);

    Page<ProductDetail> findBycolor(List<String> colors, Pageable pageable);

    Page<ProductDetail> findname(String casio, Pageable pageable);

    Page<ProductDetail> findname1(String seiko, Pageable pageable);

    Page<ProductDetail> findname2(String bentley, Pageable pageable);

    Page<ProductDetail> findname3(String citizen, Pageable pageable);

    Page<ProductDetail> findname4(String sr, Pageable pageable);

    Page<ProductDetail> findname5(String hublot, Pageable pageable);

    Page<ProductDetail> findname6(String orient, Pageable pageable);

    Page<ProductDetail> findname7(String longinet, Pageable pageable);

    Page<ProductDetail> findname8(String omega, Pageable pageable);

    Page<ProductDetail> findname9(String tissot, Pageable pageable);

    Page<ProductDetail> findAllBymoinhat(Pageable pageable);

    Page<ProductDetail> findAllBycunhat(Pageable pageable);

    // Phi
    ProductDetail findByIdUser(Integer id);

    boolean existsById(Integer id);

    void save(ProductDetail detail);

    List<ProductDetail> findAll();

    List<ProductDetail> findAllByCategory(Integer id);

    List<ProductDetail> findProductDesc();

    List<Order> findProductHot(String complete);

}
