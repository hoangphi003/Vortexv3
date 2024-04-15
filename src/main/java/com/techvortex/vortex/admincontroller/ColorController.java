package com.techvortex.vortex.admincontroller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techvortex.vortex.entity.Color;
import com.techvortex.vortex.entity.Material;
import com.techvortex.vortex.repository.ColorDAO;
import com.techvortex.vortex.service.ColorService;

@Controller
@RequestMapping("/admin")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @Autowired
    ColorDAO colorRepository;

    // @GetMapping("/color")
    // public String Color(@ModelAttribute("color") Color color, Model model) {
    //     List<Color> colors = colorRepository.findAll();
    //     model.addAttribute("color", new Color());
    //     model.addAttribute("colors", colors);
    //     return "admin/pages/Color";
    // }

    @GetMapping("/color")
    public String ListColor(@ModelAttribute("color") Color color, Model model) {
        model.addAttribute("color", new Color());
        model.addAttribute("colors", colorService.findAll());
        return "admin/pages/Color";
    }

    @GetMapping("/formcolor")
    public String FormColor(@ModelAttribute("color") Color color, Model model) {
        model.addAttribute("color", new Color());
        return "admin/pages/FormColor";
    }

   

    @PostMapping("/savecolor")
    public String addColor(@Validated @ModelAttribute("color")  Color color, BindingResult result, Model model, 
    RedirectAttributes redirectAttributes) {
        // if (result.hasErrors()) {
        //     return "admin/pages/Color";
        // }

        if (colorService.existsByNameIgnoreCase(color.getColorName())) {
            result.rejectValue("colorName", "error.material", "Tên màu đã tồn tại.");
            model.addAttribute("errorMessage", result.getFieldError("colorName").getDefaultMessage());
            model.addAttribute("colors", colorService.findAll());
            return "admin/pages/FormColor";
        }

        if (result.hasErrors()) {
            model.addAttribute("errorMessagee", "Màu không được bỏ trống");
            model.addAttribute("colors", colorService.findAll());
            return "admin/pages/FormColor";
        }

        colorRepository.save(color);
        redirectAttributes.addFlashAttribute("successMessage", "Lưu sản phẩm thành công!");
        return "redirect:/admin/formcolor";
    }
    

    @GetMapping("editColor/{colorId}")
    public String showEditForm(@PathVariable("colorId") Integer id, Model model) {
        Color color = colorService.findById(id);
        model.addAttribute("color", color);
        model.addAttribute("colors", colorService.findAll());
        return "admin/pages/FormColor";
    }

    // @PostMapping("/editColor/{id}")
    // public String editColor(@PathVariable Integer id, @ModelAttribute("color") Color color, RedirectAttributes redirectAttributes) {
    //     // Đặt ID của màu là ID đã truyền trong đường dẫn
    //     color.setColorId(id);

    //     // Lưu thông tin màu đã chỉnh sửa vào cơ sở dữ liệu
    //     colorRepository.save(color);
    //     redirectAttributes.addFlashAttribute("successEdit", "Sửa màu thành công!");

    //     // Chuyển hướng đến trang danh sách màu
    //     return "redirect:/admin/color";
    // }

    @GetMapping("/deleteColor/{colorId}")
    public String deleteColor(@PathVariable("colorId") Integer id, RedirectAttributes redirectAttributes) {
        colorRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successDelete", "Xóa màu thành công!");
        return "redirect:/admin/color";
    }
}
