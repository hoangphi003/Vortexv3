package com.techvortex.vortex.serviceimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.ReviewImg;
import com.techvortex.vortex.repository.ReviewImgDao;
import com.techvortex.vortex.service.ReviewImgUserService;

@Service
public class ReviewImgUserImpService implements ReviewImgUserService {

    @Autowired
    ReviewImgDao imgDao;

    @Override
    public void save(ReviewImg reviewImg) {
        imgDao.save(reviewImg);
    }
}
