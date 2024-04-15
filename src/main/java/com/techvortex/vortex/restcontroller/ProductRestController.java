package com.techvortex.vortex.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.techvortex.vortex.entity.Brand;
import com.techvortex.vortex.entity.Category;
import com.techvortex.vortex.entity.Color;
import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.ProductDetail;
import com.techvortex.vortex.repository.BrandDao;
import com.techvortex.vortex.repository.CategoryDAO;
import com.techvortex.vortex.repository.ColorDAO;
import com.techvortex.vortex.repository.ProductDetailDao;
import com.techvortex.vortex.service.ProductDetailService;
import com.techvortex.vortex.service.ProductService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin("*")
@RestController
public class ProductRestController {

    @Autowired
    ProductService productsService;
    @Autowired
    ProductDetailDao dao;
    @Autowired
    BrandDao dao1;
    @Autowired
    CategoryDAO dao2;
    @Autowired
    ColorDAO dao3;

    @Autowired
    ProductDetailService productService;

    @GetMapping("/findAllbrand")
    public ResponseEntity<List<Brand>> findAll() {
        List<Brand> users = dao1.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findAllcategory")
    public ResponseEntity<List<Category>> findAll1() {
        List<Category> users = dao2.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findAllcolor")
    public ResponseEntity<List<Color>> findAll2() {
        List<Color> users = dao3.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findAll")
    public ResponseEntity<Page<ProductDetail>> getProducts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "32") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findAll(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/sorted-by-price-desc")
    public ResponseEntity<Page<ProductDetail>> getProductsSortedByPriceDesc(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "32") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findAllByOrderByPriceDesc(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/sorted-by-price-asc")
    public ResponseEntity<Page<ProductDetail>> getProductsSortedByPriceAsc(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "32") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findAllByOrderByPriceAsc(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/sorted-by-name-asc")
    public ResponseEntity<Page<ProductDetail>> getProductsSortedByNameAsc(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "32") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findAllByOrderByNameAsc(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/sorted-by-price500-asc")
    public ResponseEntity<Page<ProductDetail>> getByPriceLessThanOrderByPriceAsc(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "32") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findAllByPriceLessThanOrderByPriceAsc(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/moinhat")
    public ResponseEntity<Page<ProductDetail>> getBymoinhat(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "32") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findAllBymoinhat(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/cunhat")
    public ResponseEntity<Page<ProductDetail>> getBycunhat(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "32") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findAllBycunhat(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/sorted-by-price500-asc1")
    public ResponseEntity<Page<ProductDetail>> getByPriceLessThanOrderByPriceAsc1(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findAllByPriceLessThanOrderByPriceAsc1(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/sorted-by-price-max-min")
    public ResponseEntity<List<ProductDetail>> getByPriceLessThanOrderByPriceMaxMin(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "32") int size,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice) {
        Pageable pageable = PageRequest.of(page, size);
        List<ProductDetail> products = dao.findAllByPriceInRangeOrderByPriceAsc(minPrice, maxPrice, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/filtered")
    public ResponseEntity<Page<ProductDetail>> getFilteredProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "32") int size,
            @RequestParam(name = "brands", required = false) List<String> brands) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findByBrands(brands, pageable);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/category")
    public ResponseEntity<Page<ProductDetail>> getFilteredCategory(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "32") int size,
            @RequestParam(name = "categorys", required = false) List<String> categorys) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findByCategorys(categorys, pageable);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/bycolor")
    public ResponseEntity<Page<ProductDetail>> getByColor(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "32") int size,
            @RequestParam(name = "colors", required = false) List<String> colors) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findBycolor(colors, pageable);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/user/productsearch/{keyword}")
    public List<ProductDetail> getProductByKeywords(@PathVariable("keyword") String keyword) {
        return dao.findProductByKeywords(keyword);
    }

    @GetMapping("/total")
    public int getTotalProducts() {
        return dao.getTotalProducts();
    }

    @GetMapping("/api/products")
    public ResponseEntity<Page<ProductDetail>> getBrand(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, String casio) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findname(casio, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/api/products1")
    public ResponseEntity<Page<ProductDetail>> getBrand1(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, String seiko) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findname1(seiko, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/api/products2")
    public ResponseEntity<Page<ProductDetail>> getBrand2(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, String bentley) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findname2(bentley, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/api/products3")
    public ResponseEntity<Page<ProductDetail>> getBrand3(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, String citizen) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findname3(citizen, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/api/products4")
    public ResponseEntity<Page<ProductDetail>> getBrand4(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, String sr) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findname4(sr, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/api/products5")
    public ResponseEntity<Page<ProductDetail>> getBrand5(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, String hublot) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findname5(hublot, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/api/products6")
    public ResponseEntity<Page<ProductDetail>> getBrand6(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, String orient) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findname6(orient, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/api/products7")
    public ResponseEntity<Page<ProductDetail>> getBrand7(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, String longinet) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findname7(longinet, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/api/products8")
    public ResponseEntity<Page<ProductDetail>> getBrand8(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, String omega) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findname8(omega, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/api/products9")
    public ResponseEntity<Page<ProductDetail>> getBrand9(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, String tissot) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetail> products = dao.findname9(tissot, pageable);
        return ResponseEntity.ok(products);
    }

    // Phi
    @GetMapping("/findAllProducts")
    public ResponseEntity<List<ProductDetail>> findAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/findAllProductsDesc")
    public ResponseEntity<List<ProductDetail>> findAllProductsDesc() {
        return ResponseEntity.ok(productService.findProductDesc());
    }

    @GetMapping("/productHot")
    public ResponseEntity<List<Order>> productHot() {
        return ResponseEntity.ok(productService.findProductHot("%Hoàn thành%"));
    }
}
