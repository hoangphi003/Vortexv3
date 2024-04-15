package com.techvortex.vortex.admincontroller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.entity.Authority;
import com.techvortex.vortex.entity.Role;
import com.techvortex.vortex.repository.AccountDao;
import com.techvortex.vortex.repository.AuthorityDao;
import com.techvortex.vortex.repository.RoleDao;

@Controller
@RequestMapping("/admin")
public class AuthorityController {

    @Autowired
    AccountDao accountDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    AuthorityDao authorityDao;

    @GetMapping("/authority")
    public String authority(Model model, Authority authority) {
        List<Authority> authorities = authorityDao.findAll();
        model.addAttribute("accountList", accountDao.findAll());
        model.addAttribute("roleList", roleDao.findAll());
        model.addAttribute("authorityList", authorities);
        return "admin/pages/Authority";
    }

    // @GetMapping("/addAuthority")
    // public String showAddForm(Model model, Authority authority) {
    // List<Authority> authorities = authorityDao.findAll();
    // model.addAttribute("accountList", accountDao.findAll());
    // model.addAttribute("roleList", roleDao.findAll());
    // model.addAttribute("authorityList", authorities);
    // return "admin/pages/Authority";
    // }

    // @PostMapping("/addAuthority")
    // public String addAuthority(@ModelAttribute Authority authority) {
    // authorityDao.save(authority);
    // return "redirect:/admin/authority";
    // }

    @GetMapping("/editAuthority/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, Authority authority) {
        List<Authority> authorities = authorityDao.findAll();
        Authority authoritys = authorityDao.findById(id).orElse(null);
        model.addAttribute("authority", authoritys);
        model.addAttribute("accountList", accountDao.findAll());
        model.addAttribute("roleList", roleDao.findAll());
        model.addAttribute("authorityList", authorities);
        return "admin/pages/Authority";
    }

    @PostMapping("/updateAuthority/{id}")
    public String updateAuthority(@PathVariable("id") Integer id, @ModelAttribute Authority authority,
            RedirectAttributes redirectAttributes, Principal principal) {
Authority existingAuthority = authorityDao.findById(id).orElse(null);

        if (existingAuthority != null) {
            // Lấy tên người dùng hiện tại
            String currentUserName = principal.getName();

            // Kiểm tra xem người dùng hiện tại có phải là người dùng của Authority đang
            // được cập nhật không
            if (existingAuthority.getAccount().getUserName().equals(currentUserName)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Bạn không thể sửa quyền của chính bạn.");
                return "redirect:/admin/authority";
            }

            // Cập nhật Authority nếu không phải sửa đổi quyền của bản thân
            existingAuthority.setAccount(authority.getAccount());
            existingAuthority.setRole(authority.getRole());
            authorityDao.save(existingAuthority);
            redirectAttributes.addFlashAttribute("successMessage", "Sửa quyền thành công!");
        }

        return "redirect:/admin/authority";
    }

    // @GetMapping("/deleteAuthority/{id}")
    // public String deleteAuthority(@PathVariable("id") Integer id, Principal
    // principal, Model model) {
    // String currentUserUsername = principal.getName(); // Lấy tên người dùng hiện
    // tại
    // Authority authority = authorityDao.findById(id).orElse(null);

    // if (authority != null &&
    // !authority.getAccount().getUserName().equals(currentUserUsername)) {
    // authorityDao.deleteById(id);
    // return "redirect:/admin/authority";
    // } else {
    // // Người dùng không thể xóa quyền của chính họ
    // String errorMessage = "Bạn không thể xóa quyền của chính bạn.";
    // model.addAttribute("errorMessage", errorMessage);
    // // Load lại danh sách quyền và hiển thị thông báo
    // List<Authority> authorities = authorityDao.findAll();
    // model.addAttribute("accountList", accountDao.findAll());
    // model.addAttribute("roleList", roleDao.findAll());
    // model.addAttribute("authorityList", authorities);
    // return "redirect:/admin/authority";
    // }
    // }

}