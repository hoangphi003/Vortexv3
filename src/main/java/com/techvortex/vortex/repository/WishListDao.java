package com.techvortex.vortex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvortex.vortex.entity.WishList;

public interface WishListDao extends JpaRepository<WishList, Integer> {

    @Query("select f from WishList f where f.account.UserName = :username")
    List<WishList> getFindAllFavoriteByUsername(@Param("username") String username);

    @Query("select f from WishList f where f.productDetails.ProductDetailId = :id and f.account.UserName = :username")
    WishList findByProductID(@Param("id") Integer id, @Param("username") String username);

    @Query("select COUNT(f) from WishList f where f.productDetails.ProductDetailId = :id")
    Integer countFavoriteByProductId(@Param("id") Integer id);

    // Kiệt
    
}
