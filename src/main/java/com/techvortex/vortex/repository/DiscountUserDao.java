package com.techvortex.vortex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvortex.vortex.entity.Discount;

public interface DiscountUserDao extends JpaRepository<Discount, Integer> {

    @Query("select d from Discount d where d.DiscountName = :name")
    Discount findAllDiscountCode(@Param("name") String code);

}
