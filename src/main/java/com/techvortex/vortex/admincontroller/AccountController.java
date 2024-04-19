package com.techvortex.vortex.admincontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.repository.AccountDao;
import com.techvortex.vortex.service.AccountService;

import java.util.List;
import javax.servlet.annotation.MultipartConfig;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

@Controller
@RequestMapping("/admin")
@MultipartConfig
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountDao dao;

    @GetMapping("/account")
    public String account(Model model) {
        // Tạo một đối tượng Account để binding với form
        model.addAttribute("account", new Account());
        model.addAttribute("allAccounts", dao.findStaffAccounts());

        return "admin/pages/Account";
    }

    @GetMapping("/formstaff")
    public String Formstaff(Model model) {
        // Tạo một đối tượng Account để binding với form
        model.addAttribute("account", new Account());
        model.addAttribute("allAccounts", dao.findAllCustomerAccounts());

        return "admin/pages/FormStaff";
    }

    @GetMapping("/customer")
    public String Customer(Model model) {
        // Tạo một đối tượng Account để binding với form
        model.addAttribute("account", new Account());
        model.addAttribute("allAccounts", dao.findAllCustomerAccounts());

        return "admin/pages/Customer";
    }




    // edit account
    @GetMapping("/editaccount/{Username}")
    public String showUpdateForm(@PathVariable("Username") String Username, Model model) {
        // Lấy account cần sửa
        Account account = accountService.findById(Username);
        // Kiểm tra xem account có tồn tại hay không
        if (account == null) {
            // Xử lý tùy thuộc vào yêu cầu của ứng dụng khi account không tồn tại
            return "redirect:/admin/accounts"; // Chuyển hướng đến trang danh sách accounts
        }

        // Thêm account vào model
        model.addAttribute("account", account);
        // Lấy danh sách accounts để giữ nguyên dữ liệu dưới bảng (nếu cần)
        model.addAttribute("allAccounts", dao.findStaffAccounts());
        // Trả về fragment của form
        return "admin/pages/FormStaff";
    }

    private static String UPLOAD_DIRECTORY = System.getProperty("user.dir")
            + "/src/main/resources/static/assets/images/products/";
  
            @PostMapping("/saveStaff")
            public String saveStaff(@Validated @ModelAttribute("account") Account account,
                    BindingResult bindingResult,
                    RedirectAttributes redirectAttributes, Model model) {
                
                // if (materialService.existsByNameIgnoreCase(material.getMaterialName())) {
                // bindingResult.rejectValue("MaterialName", "error.material", "Tên chất liệu đã tồn tại.");
                //  }
                // if (acc.existsByNameIgnoreCase(material.getMaterialName())) {
                //     bindingResult.rejectValue("materialName", "error.material", "Tên chất liệu đã tồn tại.");
                //     model.addAttribute("errorMessage", bindingResult.getFieldError("materialName").getDefaultMessage());
                //     model.addAttribute("allMaterial", materialService.findAll());
                //     return "admin/pages/FormMaterial";
                // }
            
                // if (bindingResult.hasErrors()) {
                //     model.addAttribute("allMaterial", materialService.findAll());
                //     return "admin/pages/Material";
                // }
                if (bindingResult.hasErrors()) {
                    // model.addAttribute("errorMessagee", "Chất liệu không được bỏ trống");
                    model.addAttribute("allAccounts", dao.findStaffAccounts());
                    return "admin/pages/FormStaff";
                }
                
                accountService.create(account);
        
                redirectAttributes.addFlashAttribute("successMessage", "Lưu tài khoản thành công!");
                redirectAttributes.addFlashAttribute("operation", "add"); // Thêm thông điệp động
        
                return "redirect:/admin/formstaff"; // Chuyển hướng đến trang danh sách accounts
            }

               
}
