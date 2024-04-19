package com.techvortex.vortex.admincontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.techvortex.vortex.entity.Brand;
import com.techvortex.vortex.entity.Category;
import com.techvortex.vortex.entity.Color;
import com.techvortex.vortex.entity.Material;
import com.techvortex.vortex.entity.Product;
import com.techvortex.vortex.entity.ProductDetail;
import com.techvortex.vortex.entity.Review;
import com.techvortex.vortex.service.BrandService;
import com.techvortex.vortex.service.CategoryService;
import com.techvortex.vortex.service.ColorService;
import com.techvortex.vortex.service.MaterialService;
import com.techvortex.vortex.service.ProductDetailService;
import com.techvortex.vortex.service.ProductService;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;

@Controller
@RequestMapping("/admin")
@MultipartConfig
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductDetailService productdetailService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    MaterialService materialService;

    @Autowired
    ColorService colorService;

    @Autowired
    BrandService brandService;

    private static String UPLOAD_DIRECTORY = "src/main/resources/static/assets/images/products/";

    @GetMapping("/product")
    public String Product(@ModelAttribute("product") Product product, Model model) {
        // model.addAttribute("product", new Product());
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allCategorys", categoryService.findAll());

        return "admin/pages/Product";
    }

    @GetMapping("/formproduct")
    public String FormProduct(@ModelAttribute("product") Product product, Model model) {
        // model.addAttribute("product", new Product());
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allCategorys", categoryService.findAll());
        return "admin/pages/FormProduct";
    }

    @PostMapping("/saveProduct")
    public String saveAccount(@Validated @ModelAttribute("product") Product product,
            BindingResult bindingResult, Category category,
            RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {

        // if (productService.existsByNameIgnoreCase(product.getProductName())) {
        // bindingResult.rejectValue("ProductName", "error.product", "Tên sản phẩm đã
        // tồn tại.");
        // model.addAttribute("errorMessage",
        // bindingResult.getFieldError("ProductName").getDefaultMessage());
        // model.addAttribute("allProducts", productService.findAll());
        // return "admin/pages/Product";
        // }

        if (bindingResult.hasErrors()) {
            model.addAttribute("allProducts", productService.findAll());
            model.addAttribute("allCategorys", categoryService.findAll());
            return "admin/pages/FormProduct";
        }

        String imageName = product.getImage().replaceAll(",", "");
        product.setImage(imageName);
        // categoryService.create(category);
        productService.create(product);

        redirectAttributes.addFlashAttribute("successMessage", "Lưu sản phẩm thành công!");
        redirectAttributes.addFlashAttribute("operation", "add"); // Thêm thông điệp động

        return "redirect:/admin/formproduct"; // Chuyển hướng đến trang danh sách accounts
    }

    // edit product
    @GetMapping("editproduct/{ProductId}")
    public String EditProduct(@PathVariable("ProductId") Integer ProductId, Model model) {
        Product product = productService.findById(ProductId);
        model.addAttribute("product", product);
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allCategorys", categoryService.findAll());

        return "admin/pages/FormProduct";
    }

    // editproductdetail
    @GetMapping("/editproductdetai/{ProductId}")
    public String editProductdetail(@PathVariable("ProductId") Integer productId, Model model) {
        Product product = productService.findById(productId);

        if (product == null) {
            model.addAttribute("errorMessage", "Không tìm thấy thông tin sản phẩm");
            return "redirect:/admin/product";
        }

        // Lấy ra ProductId từ đối tượng Product
        // Integer productIdi = product.getProductId();
        // Truy vấn tất cả các chi tiết sản phẩm của sản phẩm đó
        // List<ProductDetail> productDetail =
        // productdetailService.findAllByProductId(productIdi);
        // model.addAttribute("productDetail", productDetail);
        // Lấy danh sách các mục trong đơn hàng chi tiết của đơn hàng này
        model.addAttribute("product", product);
        List<ProductDetail> productdetails = product.getProductDetails();
        model.addAttribute("allProductdetail", productdetails);

        return "admin/pages/ProductDetails";
    }

    @GetMapping("/delete/{ProductId}")
    public String deleteProduct(@PathVariable("ProductId") Integer ProductId, RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.findById(ProductId);
            productService.delete(product);
            // Đặt thông báo thành công vào redirectAttributes
            redirectAttributes.addFlashAttribute("deletesuccessMessage", "Xóa thành công!");
        } catch (Exception e) {
            // Đặt thông báo lỗi vào redirectAttributes nếu có lỗi
            redirectAttributes.addFlashAttribute("deleteerrorMessage", "Xóa thất bại: " + e.getMessage());
        }
        // Chuyển hướng người dùng đến trang hiển thị danh sách loại sản phẩm hoặc trang
        // chính của trang quản trị
        return "redirect:/admin/product";
    }

    // edit đánh giá
    @GetMapping("/reviews/{ProductId}")
    public String showReviwsProduct(@PathVariable("ProductId") Integer ProductId, Model model) {
        Product product = productService.findById(ProductId);

        if (product == null) {
            model.addAttribute("errorMessage", "Không tìm thấy thông tin sản phẩm");
            return "redirect:/admin/order";
        }

        model.addAttribute("product", product);
        // Lấy danh sách các mục trong đơn hàng chi tiết của đơn hàng này
        List<Review> reviews = product.getReviews();
        model.addAttribute("allReview", reviews);

        return "admin/pages/Reviews";
    }

}
