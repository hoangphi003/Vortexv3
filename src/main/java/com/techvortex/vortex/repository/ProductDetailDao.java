package com.techvortex.vortex.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.ProductDetail;

@Repository
public interface ProductDetailDao extends JpaRepository<ProductDetail, Integer> {

    @Query("SELECT o FROM ProductDetail o WHERE o.product.ProductName LIKE %:keywords%")
    List<ProductDetail> findProductByKeywords(@Param("keywords") String keyword);

    Page<ProductDetail> findAll(Pageable pageable);

    @Query("SELECT o FROM ProductDetail o WHERE o.brand.BrandName = 'casio' ")
    Page<ProductDetail> findname(String casio, Pageable pageable);

    @Query("SELECT o FROM ProductDetail o WHERE o.brand.BrandName = 'seiko' ")
    Page<ProductDetail> findname1(String seiko, Pageable pageable);

    @Query("SELECT o FROM ProductDetail o WHERE o.brand.BrandName = 'bentley' ")
    Page<ProductDetail> findname2(String bentley, Pageable pageable);

    @Query("SELECT o FROM ProductDetail o WHERE o.brand.BrandName = 'citizen' ")
    Page<ProductDetail> findname3(String citizen, Pageable pageable);

    @Query("SELECT o FROM ProductDetail o WHERE o.brand.BrandName = 'sr' ")
    Page<ProductDetail> findname4(String sr, Pageable pageable);

    @Query("SELECT o FROM ProductDetail o WHERE o.brand.BrandName = 'hublot' ")
    Page<ProductDetail> findname5(String hublot, Pageable pageable);

    @Query("SELECT o FROM ProductDetail o WHERE o.brand.BrandName = 'orient' ")
    Page<ProductDetail> findname6(String orient, Pageable pageable);

    @Query("SELECT o FROM ProductDetail o WHERE o.brand.BrandName = 'longinet' ")
    Page<ProductDetail> findname7(String longinet, Pageable pageable);

    @Query("SELECT o FROM ProductDetail o WHERE o.brand.BrandName = 'omega' ")
    Page<ProductDetail> findname8(String omega, Pageable pageable);

    @Query("SELECT o FROM ProductDetail o WHERE o.brand.BrandName = 'tissot' ")
    Page<ProductDetail> findname9(String tissot, Pageable pageable);

    @Query("SELECT p FROM ProductDetail p ORDER BY p.product.Price DESC")
    Page<ProductDetail> findAllByOrderByPriceDesc(Pageable pageable);

    @Query("SELECT p FROM ProductDetail p ORDER BY p.product.Price ASC")
    Page<ProductDetail> findAllByOrderByPriceAsc(Pageable pageable);

    @Query("SELECT p FROM ProductDetail p ORDER BY p.product.ProductName ASC")
    Page<ProductDetail> findAllByOrderByNameAsc(Pageable pageable);

    @Query("SELECT p FROM ProductDetail p ORDER BY p.ProductDetailId DESC")
    Page<ProductDetail> findAllBymoinhat(Pageable pageable);

    @Query("SELECT p FROM ProductDetail p ORDER BY p.ProductDetailId ASC")
    Page<ProductDetail> findAllBycunhat(Pageable pageable);

    @Query("SELECT p FROM ProductDetail p WHERE p.product.Price < 500000 ORDER BY p.product.Price ASC")
    Page<ProductDetail> findAllByPriceLessThanOrderByPriceAsc(Pageable pageable);

    @Query("SELECT p FROM ProductDetail p WHERE p.product.Price > 500000 ORDER BY p.product.Price ASC")
    Page<ProductDetail> findAllByPriceLessThanOrderByPriceAsc1(Pageable pageable);

    @Query("SELECT p FROM ProductDetail p WHERE (:minPrice IS NULL OR p.product.Price >= :minPrice) AND (:maxPrice IS NULL OR p.product.Price <= :maxPrice)")
    List<ProductDetail> findAllByPriceInRangeOrderByPriceAsc(Double minPrice, Double maxPrice, Pageable pageable);

    @Query("SELECT p FROM ProductDetail p WHERE p.brand.BrandName IN :brands")
    Page<ProductDetail> findByBrands(List<String> brands, Pageable pageable);

    @Query("SELECT p FROM ProductDetail p WHERE p.product.category.CategoryName IN :categorys")
    Page<ProductDetail> findByCategorys(List<String> categorys, Pageable pageable);

    @Query("SELECT p FROM ProductDetail p WHERE p.color.ColorName IN :colors")
    Page<ProductDetail> findBycolor(List<String> colors, Pageable pageable);

    @Query("SELECT COUNT(p) FROM ProductDetail p")
    int getTotalProducts();

    // Phi
    @Query("select p from ProductDetail p where p.product.category.CategoryId = :id")
    List<ProductDetail> findAllByCategory(@Param("id") Integer id);

    @Query("SELECT p FROM ProductDetail p ORDER BY p.ProductDetailId DESC")
    List<ProductDetail> findAllByDesc();

    @Query("select o from Order o where o.OrderStatus like :status")
    List<Order> findProductHotByKeyword(@Param("status") String complete);

}
