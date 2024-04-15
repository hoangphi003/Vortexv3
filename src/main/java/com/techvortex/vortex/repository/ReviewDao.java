package com.techvortex.vortex.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techvortex.vortex.entity.Review;


@Repository
public interface ReviewDao extends JpaRepository<Review, Integer> {
    
}
