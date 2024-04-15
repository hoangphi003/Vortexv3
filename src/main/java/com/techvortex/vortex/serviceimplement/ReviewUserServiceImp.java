package com.techvortex.vortex.serviceimplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Review;
import com.techvortex.vortex.repository.ReviewUserDao;
import com.techvortex.vortex.service.ReviewUserService;

@Service
public class ReviewUserServiceImp implements ReviewUserService {

    @Autowired
    ReviewUserDao reviewUserDao;

    @Override
    public List<Review> findAll() {
        return reviewUserDao.findAll();
    }

    @Override
    public void save(Review review) {
        reviewUserDao.save(review);
    }

    @Override
    public Integer countReview(Integer productId) {
        return reviewUserDao.countReviewProduct(productId);
    }

    @Override
    public Integer getAvgReview(Integer productId) {
        return reviewUserDao.getAvgReviewByProductId(productId);
    }

    @Override
    public Integer getCountReviewThan4S(Integer productId) {
        return reviewUserDao.getCountReviewThan4SById(productId);
    }

    @Override
    public List<Review> getChooseUserStart(Integer productId, Integer start) {
        return reviewUserDao.getChooseStartByProductId(productId, start);
    }

    @Override
    public Integer findReviewByProductId(Integer productId) {
        return reviewUserDao.findReviewByProductIdAndAvenger(productId);
    }
}
