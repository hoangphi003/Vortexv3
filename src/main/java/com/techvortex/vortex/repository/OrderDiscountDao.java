package com.techvortex.vortex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techvortex.vortex.entity.OrderDiscount;

public interface OrderDiscountDao extends JpaRepository<OrderDiscount, Integer> {

}
