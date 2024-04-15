package com.techvortex.vortex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvortex.vortex.entity.Cart;

import java.util.List;

import javax.transaction.Transactional;

public interface CartDao extends JpaRepository<Cart, Integer> {

    @Query("SELECT c FROM Cart c WHERE c.account.UserName = :username AND c.productDetails.ProductDetailId = :productDetailId")
    Cart findByAccount_ProductDetailId(@Param("username") String UserName,
            @Param("productDetailId") Integer productDetailId);

    @Query("select c from Cart c where c.account.UserName = :username")
    List<Cart> findAllCartByUserName(@Param("username") String userName);

    @Query("select c from Cart c where c.CartId = :id and c.account.UserName = :username")
    Cart findCartById(@Param("username") String userName, @Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("delete from Cart c where c.account.UserName = :username")
    void deleteAllCart(@Param("username") String userName);

    @Query("select count(c) from Cart c where c.account.UserName = :username")
    Long countQty(@Param("username") String userName);

    @Query("select c from Cart c where c.productDetails.ProductDetailId = :detailId and c.account.UserName = :username")
    Cart findByProductDetailIdCart(@Param("detailId") Integer detailId,@Param("username") String username);

    @Query("select c from Cart c where c.account.UserName = :username")
    List<Cart> findAllByUsername(@Param("username") String username);

    @Query("select count(c) from Cart c where c.account.UserName = :username")
    Integer getQtyCartThenCount(String username);

}
