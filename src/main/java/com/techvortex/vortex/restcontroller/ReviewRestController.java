package com.techvortex.vortex.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.OrderDetail;
import com.techvortex.vortex.entity.Review;
import com.techvortex.vortex.entity.ReviewImg;
import com.techvortex.vortex.service.OrderDetailService;
import com.techvortex.vortex.service.OrderService;
import com.techvortex.vortex.service.ReviewImgUserService;
import com.techvortex.vortex.service.ReviewService;
import com.techvortex.vortex.service.ReviewUserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
public class ReviewRestController {

    @Autowired
    ReviewUserService reviewService;

    @Autowired
    ReviewImgUserService reviewImgUserService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/findorderid/{id}")
    public ResponseEntity<Order> findOrderIdToReview(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @GetMapping("/findorderdetailid/{id}")
    public ResponseEntity<OrderDetail> findOrderDetailId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderDetailService.findById(id));
    }

    @GetMapping("/setreviewed/{id}")
    public ResponseEntity<OrderDetail> setReviewed(@PathVariable("id") Integer id) {
        OrderDetail orderDetail = orderDetailService.findById(id);
        orderDetail.setReviewed(true);

        orderDetailService.save(orderDetail);
        return ResponseEntity.ok(orderDetail);
    }

    @GetMapping("/findallreviews")
    public ResponseEntity<List<Review>> getMethodName() {
        return ResponseEntity.ok(reviewService.findAll());
    }

    @GetMapping("/findreviewsbyid/{productId}")
    public ResponseEntity<?> getFindAllByProduct(@PathVariable("productId") Integer productId) {
        return ResponseEntity.ok(reviewService.findReviewByProductId(productId));
    }

    @PostMapping("/postreview")
    public ResponseEntity<Review> postReview(@RequestBody Review review) {
        reviewService.save(review);

        for (ReviewImg x : review.getReviewImgs()) {
            ReviewImg reviewImg = new ReviewImg();
            reviewImg.setImage(x.getImage());
            reviewImg.setReview(review);
            reviewImgUserService.save(reviewImg);
        }
        return ResponseEntity.ok().build();

    }

    @GetMapping("/countreview/{productId}")
    public ResponseEntity<Integer> findReview(@PathVariable("productId") Integer productId) {
        return ResponseEntity.ok(reviewService.countReview(productId));
    }

    @GetMapping("/avgreview/{productId}")
    public ResponseEntity<Integer> avgReview(@PathVariable("productId") Integer productId) {
        return ResponseEntity.ok(reviewService.getAvgReview(productId));
    }

    @GetMapping("/countreviewthen4s/{productId}")
    public ResponseEntity<Integer> getCountReviewThan4S(@PathVariable("productId") Integer productId) {
        return ResponseEntity.ok(reviewService.getCountReviewThan4S(productId));
    }

    @GetMapping("/filterchoosestart/{productId}/{start}")
    public ResponseEntity<?> filterChooseStart(@PathVariable("productId") Integer productId,
            @PathVariable("start") Integer start) {
        return ResponseEntity.ok(reviewService.getChooseUserStart(productId, start));
    }

}
