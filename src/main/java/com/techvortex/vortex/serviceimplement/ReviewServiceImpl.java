package com.techvortex.vortex.serviceimplement;
import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.techvortex.vortex.entity.Review;
import com.techvortex.vortex.repository.ReviewDao;
import com.techvortex.vortex.service.CategoryService;
import com.techvortex.vortex.service.ReviewService;

import javax.transaction.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService {
    
    @Autowired
    ReviewDao reviewdao;

    @Override
    public List<Review> findAll() {
        // TODO Auto-generated method stub
        return reviewdao.findAll();
    }

    @Override
    @Transactional
    public Review findById(Integer ReviewId) {
        // TODO Auto-generated method stub
        return reviewdao.findById(ReviewId).get();
    }

    @Override
    public Review create(Review review) {
        // TODO Auto-generated method stub
        return reviewdao.save(review);
    }

    @Override
    public Review update(Review review) {
        // TODO Auto-generated method stub
        return reviewdao.save(review);
    }

    @Override
    public void delete(Review review) {
        // TODO Auto-generated method stub
        reviewdao.delete(review);
    }
    
}
