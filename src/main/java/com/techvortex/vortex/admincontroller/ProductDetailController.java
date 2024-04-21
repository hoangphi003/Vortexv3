package com.techvortex.vortex.admincontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import com.techvortex.vortex.entity.ProductDetail;
import com.techvortex.vortex.service.BrandService;
import com.techvortex.vortex.service.CategoryService;
import com.techvortex.vortex.service.ColorService;
import com.techvortex.vortex.service.MaterialService;
import com.techvortex.vortex.service.ProductDetailService;
import com.techvortex.vortex.service.ProductDetailServiceAdmin;
import com.techvortex.vortex.service.ProductService;
import com.techvortex.vortex.entity.Brand;
import com.techvortex.vortex.entity.Category;
import com.techvortex.vortex.entity.Color;
import com.techvortex.vortex.entity.Material;
import com.techvortex.vortex.entity.Product;

@Controller
@RequestMapping("/admin")
public class ProductDetailController {
    @Autowired
    ProductDetailServiceAdmin productdeService;

    @Autowired
    ProductService productService;

    @Autowired
    MaterialService materialService;

    @Autowired
    ColorService colorService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;


    @GetMapping("/productdetail")
    public String Product(@ModelAttribute("productDetail") ProductDetail productDetail,
            Color color, Brand brand, Material material, Product product, Model model) {
        model.addAttribute("allProductdetail", productdeService.findAll());
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allBrand", brandService.findAll());
        model.addAttribute("allColor", colorService.findAll());
        model.addAttribute("allMaterial", materialService.findAll());

        return "admin/pages/ProductDetails";
    }

    @GetMapping("/inventoryproducts")
    public String InventoryProducts(@ModelAttribute("productDetail") ProductDetail productDetail,
            Color color, Brand brand, Material material, Product product,Category category, Model model) {
        model.addAttribute("allProductdetail", productdeService.findAll());
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allBrand", brandService.findAll());
        model.addAttribute("allColor", colorService.findAll());
        model.addAttribute("allMaterial", materialService.findAll());
        model.addAttribute("allCategory", categoryService.findAll());
        return "admin/pages/inventoryproducts";
    }


    @GetMapping("/editproductdetail/{ProductId}")
    public String editProductDetail(@PathVariable("ProductId") Integer productId, Model model) {
        Product product = productService.findById(productId);

        if (product == null) {
            model.addAttribute("errorMessage", "Không tìm thấy thông tin sản phẩm");
            return "redirect:/admin/product";
        }
        model.addAttribute("allProductdetail", productdeService.findAll());
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allBrand", brandService.findAll());
        model.addAttribute("allColor", colorService.findAll());
        model.addAttribute("allMaterial", materialService.findAll());

        // Tạo một ProductDetail và đặt Product vào đó
        model.addAttribute("product", product);
        List<ProductDetail> productdetails = product.getProductDetails();
        model.addAttribute("allProductdetail", productdetails);

        ProductDetail productDetail = new ProductDetail();
        productDetail.setProduct(product);

        // Truyền thông tin của sản phẩm sang form chỉnh sửa của ProductDetail
        model.addAttribute("productDetail", productDetail);

        return "admin/pages/ProductDetails";
    }

    // lưu sản phẩm
    @PostMapping("/saveProductdetail")
    public String saveAccount(@ModelAttribute("productDetail") ProductDetail productDetail,
            Color color, Brand brand, Material material,
            RedirectAttributes redirectAttributes, Model model) {

        // if (bindingResult.hasErrors()) {
        // model.addAttribute("allProductdetail", productdeService.findAll());
        // model.addAttribute("allProducts", productService.findAll());
        // model.addAttribute("allBrand", brandService.findAll());
        // model.addAttribute("allColor", colorService.findAll());
        // model.addAttribute("allMaterial", materialService.findAll());
        // // model.addAttribute("allCategorys", categoryService.findAll());
        // return "admin/pages/ProductDetails";
        // }

        // brandService.create(brand);
        // colorService.create(color);
        // materialService.create(material);
        productdeService.create(productDetail);
        // Lấy ID của sản phẩm chi tiết vừa lưu
       

        redirectAttributes.addFlashAttribute("successMessage", "Lưu sản phẩm thành công!");
        redirectAttributes.addFlashAttribute("operation", "add"); // Thêm thông điệp động

        return "redirect:/admin/productdetail" ; // Lấy ID của sản phẩm từ sản phẩm chi tiết vừa lưu
       
    }

    @GetMapping("/deleteproductdetail/{ProductDetailId}")
    public String deleteProductdetail(@PathVariable("ProductDetailId") Integer ProductDetailId,
            @RequestParam(required = false) Integer ProductId,
            @RequestParam(required = false) Integer ColorId,
            @RequestParam(required = false) Integer MaterialId,
            @RequestParam(required = false) Integer BrandId,
            RedirectAttributes redirectAttributes) {
        try {
            // Kiểm tra xem ProductDetailId có khác null không
            if (ProductDetailId != null) {
                // Kiểm tra và xóa Color
                if (ColorId != null) {
                    Color color = colorService.findById(ColorId);
                    if (color != null) {
                        System.out.println("ID color: " + ColorId);
                        colorService.delete(color);
                    }
                }

                // Kiểm tra và xóa Material
                if (MaterialId != null) {
                    Material material = materialService.findById(MaterialId);
                    if (material != null) {
                        System.out.println("ID chất liệu: " + MaterialId);
                        materialService.delete(material);
                    }
                }

                // Kiểm tra và xóa Brand
                if (BrandId != null) {
                    Brand brand = brandService.findById(BrandId);
                    if (brand != null) {
                        System.out.println("ID thương hiệu: " + BrandId);
                        brandService.delete(brand);
                    }
                }

                // Kiểm tra và xóa Product
                if (ProductId != null) {
                    Product product = productService.findById(ProductId);
                    if (product != null) {
                        System.out.println("ID sản phẩm: " + ProductId);
                        productService.delete(product);
                    }
                }

                // Xóa ProductDetail
                ProductDetail productdetail = productdeService.findById(ProductDetailId);
                if (productdetail != null) {
                    System.out.println("ID chi tiết sản phẩm: " + ProductDetailId);
                    productdeService.delete(productdetail);
                }

                // Đặt thông báo thành công vào redirectAttributes
                redirectAttributes.addFlashAttribute("deletesuccessMessage", "Xóa thành công!");
            } else {
                // Ném ngoại lệ nếu ProductDetailId là null
                throw new IllegalArgumentException("ProductDetailId is null.");
            }
        } catch (Exception e) {
            // Đặt thông báo lỗi vào redirectAttributes nếu có lỗi
            System.out.println(e);
            redirectAttributes.addFlashAttribute("deleteerrorMessage", "Xóa thất bại: " + e.getMessage());
        }
        // Chuyển hướng người dùng đến trang hiển thị danh sách loại sản phẩm hoặc trang
        // chính của trang quản trị
        return "redirect:/admin/productdetail";
    }

    // edit product
    @GetMapping("editproductdetaill/{ProductDetailId}")
    public String EditProductdetaill(@PathVariable("ProductDetailId") Integer ProductDetailId, Model model) {

        ProductDetail productDetail = productdeService.findById(ProductDetailId);
        model.addAttribute("productDetail", productDetail);
        ProductDetail productDetaill = new ProductDetail();

        model.addAttribute("allProductdetail", productdeService.findAll());
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allBrand", brandService.findAll());
        model.addAttribute("allColor", colorService.findAll());
        model.addAttribute("allMaterial", materialService.findAll());

        return "admin/pages/ProductDetails";
    }

}
