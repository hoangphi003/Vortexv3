package com.techvortex.vortex.service;

import java.util.List;

import com.techvortex.vortex.entity.Review;

public interface ReviewUserService {

    List<Review> findAll();

    void save(Review review);

    Integer countReview(Integer productId);

    Integer getAvgReview(Integer productId);

    Integer getCountReviewThan4S(Integer productId);

    List<Review> getChooseUserStart(Integer productId, Integer start);

    Integer findReviewByProductId(Integer productId);

}
