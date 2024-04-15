package com.techvortex.vortex.admincontroller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techvortex.vortex.entity.Category;
import com.techvortex.vortex.entity.Material;
import com.techvortex.vortex.repository.CategoryDAO;
import com.techvortex.vortex.service.CategoryService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryDAO categoryDao;

    @Autowired
    ServletContext app;

    // @GetMapping("/category")
    // public String Category(Model model, Category category) {
    // List<Category> categories = categoryService.getAllCategories();
    // model.addAttribute("categories", categories);
    // return "admin/pages/Category";
    // }

    @GetMapping("/category")
    public String Material(@Validated @ModelAttribute("category") Category category, BindingResult result, Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/pages/Category";
    }

    @GetMapping("/formcategory")
    public String Formcategory( @ModelAttribute("category") Category category,Model model) {
        model.addAttribute("category", new Category()); 
        return "admin/pages/FormCategory";
    }

    // @GetMapping("/addCategory")
    // public String showAddForm(Model model, Category category) {
    // model.addAttribute("category", new Category());
    // return "admin/pages/Category";
    // }

    @PostMapping("/addCategory")
    public String addCategory(@Validated @ModelAttribute("category") Category category, BindingResult result,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "imageFile", required = false) MultipartFile img, Model model) throws IOException {

        if (result.hasErrors()) {
            // No need to create a new Category instance here since the existing one (with
            // errors) should be sent back
            model.addAttribute("categories", categoryService.findAll());
            return "admin/pages/FormCategory"; // Ensure this is the correct path to your form
        }

        if (categoryService.existsByNameIgnoreCase(category.getCategoryName())) {
            result.rejectValue("categoryName", "error.material", "Tên phân loại đã tồn tại.");
            model.addAttribute("errorMessage", result.getFieldError("categoryName").getDefaultMessage());
            model.addAttribute("categories", categoryService.findAll());
            return "admin/pages/FormCategory";
        }
        
        if (img.isEmpty()) {
            result.rejectValue("categoryImage", "error.categoryImage", "Hình ảnh không được để trống");
            model.addAttribute("categories", categoryService.findAll());
            return "admin/pages/FormCategory";
        }

        if (!img.isEmpty()) {
            String filename = img.getOriginalFilename();
            String uploadDirectory = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
                    + File.separator + "resources" + File.separator + "static" + File.separator + "assets"
                    + File.separator + "imagesadmin" + File.separator;
            File uploadFolder = new File(uploadDirectory);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            File destFile = new File(uploadFolder, filename);
            img.transferTo(destFile);
        
            category.setCategoryImage(filename);
        } 
        
        // Tiếp tục xử lý việc thêm danh mục
        categoryService.create(category);
        redirectAttributes.addFlashAttribute("successMessage", "Lưu phân loại thành công!");
        return "redirect:/admin/formcategory";
    }

    // @GetMapping("/editCategory/{id}")
    // public String showEditForm(@PathVariable Integer id, Model model) {
    // // Lấy danh mục cần chỉnh sửa từ id
    // Category category = categoryService.getCategoryById(id);
    // // Truyền danh mục và id của nó vào model để sử dụng trong form
    // model.addAttribute("category", category);
    // return "admin/pages/Category"; // Thay thế "EditCategory" bằng tên của trang
    // HTML dùng để chỉnh sửa danh mục
    // }

    @GetMapping("editCategory/{categoryId}")
    public String EditMaterial(@PathVariable("categoryId") Integer id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        model.addAttribute("categories", categoryService.findAll());
        return "admin/pages/FormCategory";
    }

    // @PostMapping("/editCategory/{id}")
    // public String editCategory(@PathVariable Integer id,
    // @ModelAttribute("category") Category category,
    // @RequestParam(value = "imageFile", required = false) MultipartFile img)
    // throws IOException {
    // if (img != null && !img.isEmpty()) {
    // String filename = img.getOriginalFilename();
    // String uploadDirectory = System.getProperty("user.dir") + File.separator +
    // "src" + File.separator + "main" + File.separator + "resources" +
    // File.separator + "static" + File.separator + "assets" + File.separator +
    // "imagesadmin" + File.separator;
    // File uploadFolder = new File(uploadDirectory);
    // if (!uploadFolder.exists()) {
    // uploadFolder.mkdirs();
    // }
    // File destFile = new File(uploadFolder, filename);
    // img.transferTo(destFile);

    // category.setCategoryImage(filename);
    // System.out.println(uploadFolder);
    // System.out.println(filename);
    // }
    // // Thiết lập lại id cho đối tượng category
    // category.setCategoryId(id);
    // // Tiếp tục xử lý việc cập nhật danh mục
    // categoryService.editCategory(category);
    // return "redirect:/admin/category";
    // }

    @GetMapping("/deleteCategory/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") Integer id, Category category,
            RedirectAttributes redirectAttributes) {
        categoryService.delete(category);
        redirectAttributes.addFlashAttribute("deleteCategory", "Xóa phân loại thành công!");
        return "redirect:/admin/category";
    }
}
