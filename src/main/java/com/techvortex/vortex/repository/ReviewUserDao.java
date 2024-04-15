package com.techvortex.vortex.repository;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techvortex.vortex.entity.Review;

@Repository
public interface ReviewUserDao extends JpaRepository<Review, Integer> {

    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :productId")
    Integer countReviewProduct(@Param("productId") Integer productId);

    @Query("select AVG(r.Rating) from Review r where r.product.id = :productId")
    Integer getAvgReviewByProductId(@Param("productId") Integer productId);

    @Query("select COUNT(r) from Review r where r.product.id = :productId and r.Rating >= 4")
    Integer getCountReviewThan4SById(@Param("productId") Integer productId);

    @Query("select r from Review r where r.product.id = :productId and r.Rating = :start")
    List<Review> getChooseStartByProductId(@Param("productId") Integer productId, @Param("start") Integer start);

    @Query("select r from Review r where r.product.id = :productId and r.Rating = :start")
    Integer findReviewByProductIdAndAvenger(@Param("productId") Integer productId);

}
