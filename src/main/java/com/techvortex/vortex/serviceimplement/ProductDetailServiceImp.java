package com.techvortex.vortex.serviceimplement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.ProductDetail;
import com.techvortex.vortex.repository.ProductDetailDao;
import com.techvortex.vortex.service.ProductDetailService;
import com.techvortex.vortex.service.ProductDetailServiceAdmin;

@Service
public class ProductDetailServiceImp implements ProductDetailService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    ProductDetailDao productDetailDao;

    @Override
    public ProductDetail findById(Integer id) {
        return productDetailDao.findById(id).get();
    }

    @Override
    public Page<ProductDetail> findAll(Pageable pageable) {
        return productDetailDao.findAll(pageable);
    }

    @Override
    public Page<ProductDetail> findAllByOrderByPriceDesc(Pageable pageable) {
        return productDetailDao.findAllByOrderByPriceDesc(pageable);
    }

    @Override
    public Page<ProductDetail> findAllByOrderByPriceAsc(Pageable pageable) {
        return productDetailDao.findAllByOrderByPriceAsc(pageable);
    }

    @Override
    public Page<ProductDetail> findAllByOrderByNameAsc(Pageable pageable) {
        return productDetailDao.findAllByOrderByNameAsc(pageable);
    }

    @Override
    public Page<ProductDetail> findAllByPriceLessThanOrderByPriceAsc(Pageable pageable) {
        return productDetailDao.findAllByPriceLessThanOrderByPriceAsc(pageable);
    }

    @Override
    public Page<ProductDetail> findAllByPriceLessThanOrderByPriceAsc1(Pageable pageable) {
        return productDetailDao.findAllByPriceLessThanOrderByPriceAsc1(pageable);
    }

    @Override
    public List<ProductDetail> findAllByPriceInRangeOrderByPriceAsc(Double minPrice, Double maxPrice,
            Pageable pageable) {
        return productDetailDao.findAllByPriceInRangeOrderByPriceAsc(minPrice, maxPrice, pageable);
    }

    @Override
    public Page<ProductDetail> findByBrands(List<String> brands, Pageable pageable) {

        return productDetailDao.findByBrands(brands, pageable);

    }

    @Override
    public Page<ProductDetail> findByCategorys(List<String> categorys, Pageable pageable) {

        return productDetailDao.findByCategorys(categorys, pageable);

    }

    @Override
    public Page<ProductDetail> findBycolor(List<String> colors, Pageable pageable) {

        return productDetailDao.findBycolor(colors, pageable);

    }

    @Override
    public Page<ProductDetail> findname(String casio, Pageable pageable) {
        return productDetailDao.findname(casio, pageable);
    }

    @Override
    public Page<ProductDetail> findname1(String seiko, Pageable pageable) {
        return productDetailDao.findname1(seiko, pageable);
    }

    @Override
    public Page<ProductDetail> findname2(String bentley, Pageable pageable) {
        return productDetailDao.findname1(bentley, pageable);
    }

    @Override
    public Page<ProductDetail> findname3(String citizen, Pageable pageable) {
        return productDetailDao.findname1(citizen, pageable);
    }

    @Override
    public Page<ProductDetail> findname4(String sr, Pageable pageable) {
        return productDetailDao.findname1(sr, pageable);
    }

    @Override
    public Page<ProductDetail> findname5(String hublot, Pageable pageable) {
        return productDetailDao.findname1(hublot, pageable);
    }

    @Override
    public Page<ProductDetail> findname6(String orient, Pageable pageable) {
        return productDetailDao.findname1(orient, pageable);
    }

    @Override
    public Page<ProductDetail> findname7(String longinet, Pageable pageable) {
        return productDetailDao.findname1(longinet, pageable);
    }

    @Override
    public Page<ProductDetail> findname8(String omega, Pageable pageable) {
        return productDetailDao.findname1(omega, pageable);
    }

    @Override
    public Page<ProductDetail> findname9(String tissot, Pageable pageable) {
        return productDetailDao.findname1(tissot, pageable);
    }

    @Override
    public Page<ProductDetail> findAllBymoinhat(Pageable pageable) {
        return productDetailDao.findAllBymoinhat(pageable);
    }

    @Override
    public Page<ProductDetail> findAllBycunhat(Pageable pageable) {
        return productDetailDao.findAllBycunhat(pageable);
    }

    // Phi

    @Override
    public ProductDetail findByIdUser(Integer id) {
        return productDetailDao.findById(id).get();
    }

    @Override
    public boolean existsById(Integer id) {
        return productDetailDao.existsById(id);
    }

    @Override
    public void save(ProductDetail detail) {
        productDetailDao.save(detail);
    }

    @Override
    public List<ProductDetail> findAll() {
        return productDetailDao.findAll();
    }

    @Override
    public List<ProductDetail> findAllByCategory(Integer id) {
        return productDetailDao.findAllByCategory(id);
    }

    @Override
    public List<ProductDetail> findProductDesc() {
        return productDetailDao.findAllByDesc();
    }

    @Override
    public List<Order> findProductHot(String complete) {
        return productDetailDao.findProductHotByKeyword(complete);
    }
}
