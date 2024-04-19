package com.techvortex.vortex.admincontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.annotation.MultipartConfig;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.entity.Review;
import com.techvortex.vortex.service.AccountService;
import com.techvortex.vortex.service.ProductService;
import com.techvortex.vortex.service.ReviewService;

@Controller
@RequestMapping("/admin")
@MultipartConfig
public class ReviewsController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    AccountService accountService;

    @Autowired
    ProductService productService;

    private static String UPLOAD_DIRECTORY = "src/main/resources/static/assets/images/products/";

    @GetMapping("/reviews")
    public String Reviews(@ModelAttribute("review") Review review, Account account, Model model) {
        model.addAttribute("allReview", reviewService.findAll());
        model.addAttribute("allAccount", accountService.findAll());
        model.addAttribute("allProducts", productService.findAll());
        return "admin/pages/Reviews";
    }

    @PostMapping("/saveReview")
    public String SaveReview(@ModelAttribute("review") Review review,
            @RequestParam("productName") String productName,
            @RequestParam("productImage") String productImage,
            Model model) {

        // review.setProductName(productName);
        // review.setProductImage(productImage);
        reviewService.create(review);

        return "admin/pages/Reviews";
    }

}
