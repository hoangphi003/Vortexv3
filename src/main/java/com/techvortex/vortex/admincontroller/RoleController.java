package com.techvortex.vortex.admincontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techvortex.vortex.entity.Role;
import com.techvortex.vortex.service.RoleService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/role")
    public String FillAllRole(@ModelAttribute("role") Role role, Model model) {
        // model.addAttribute("role", new Role());
        model.addAttribute("findAll", roleService.findAll());
        return "admin/pages/Role";
    }

    @GetMapping("/formrole")
    public String Formrole(@ModelAttribute("role") Role role, Model model) {
        // model.addAttribute("role", new Role());
        model.addAttribute("findAll", roleService.findAll());
        return "admin/pages/FormRole";
    }

    @PostMapping("/saverole")
public String saveRole(@ModelAttribute("role") Role role, Model model,
RedirectAttributes redirectAttributes) {
    // Kiểm tra nếu mã và tên vai trò không giống nhau
    if (!role.getRoleId().equals(role.getRoleName())) {
        model.addAttribute("errorMessagee", "Mã vai trò và tên vai trò phải giống nhau!");
        model.addAttribute("findAll", roleService.findAll());
        return "admin/pages/FormRole"; // Trả về trang thêm mới với thông báo lỗi
    }

    // Kiểm tra xem vai trò đã tồn tại trong cơ sở dữ liệu chưa
    // if (roleService.existsByRoleCode(role.getRoleId())) {
    //     model.addAttribute("error", "Vai trò đã tồn tại.");
    //     return "admin/addRole"; // Trả về trang thêm mới với thông báo lỗi
    // }

    // Lưu vai trò vào cơ sở dữ liệu
    roleService.save(role);
    redirectAttributes.addFlashAttribute("successMessage", "Lưu vai trò thành công!");
    return "redirect:/admin/formrole";
}


@GetMapping("editrole/{RoleId}")
public String EditRole(@PathVariable("RoleId") String RoleId, Model model) {
    Role role = roleService.findById(RoleId);
    model.addAttribute("role", role);
    model.addAttribute("findAll", roleService.findAll());
    return "admin/pages/FormRole";
}

@RequestMapping(value = "/delete/{RoleId}", params = "role")
public String deleterole(@PathVariable("RoleId") String RoleId, 
        RedirectAttributes redirectAttributes) {
    
            if (RoleId != null && !RoleId.trim().isEmpty()) {
                try {
                    int roleIdAsInt = Integer.parseInt(RoleId);
                    // Tiếp tục xử lý với roleIdAsInt
                    // ...
                } catch (NumberFormatException e) {
                    // Xử lý ngoại lệ chuyển đổi số nguyên
                    e.printStackTrace(); // hoặc log ngoại lệ theo cách bạn muốn
                }
            } else {
                throw new IllegalArgumentException("Invalid roleId: " + RoleId);
            }
            
            try {
                Role role = roleService.findById(RoleId);
                if (role != null) {
                    roleService.delete(role);
                    // Đặt thông báo thành công vào redirectAttributes
                    redirectAttributes.addFlashAttribute("deletesuccessMessage", "Xóa thành công!");
                } else {
                    // Đối tượng không tồn tại, xử lý hoặc báo lỗi tùy thuộc vào yêu cầu của bạn
                    // ...
                }
            } catch (Exception e) {
                // Đặt thông báo lỗi vào redirectAttributes nếu có lỗi
                redirectAttributes.addFlashAttribute("deleteerrorMessage", "Xóa thất bại: " + e.getMessage());
            }
            
    // Chuyển hướng người dùng đến trang hiển thị danh sách loại sản phẩm hoặc trang
    // chính của trang quản trị
    return "redirect:/admin/role";
}

}
