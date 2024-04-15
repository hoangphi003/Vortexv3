package com.techvortex.vortex.admincontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techvortex.vortex.entity.WishList;
import com.techvortex.vortex.service.WishListService;

@Controller
@RequestMapping("/admin")
public class FavoriteController {

    @Autowired
    WishListService favoriteService;

    @GetMapping("/favorite")
    public String Favorite(@ModelAttribute("favorite") WishList color, Model model) {
        model.addAttribute("favorite", new WishList());
        model.addAttribute("favorites", favoriteService.findAll());
        return "admin/pages/Favorite";
    }

}
