package com.techvortex.vortex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techvortex.vortex.entity.Discount;
import com.techvortex.vortex.entity.Order;

public interface CheckoutDao extends JpaRepository<Order, Integer> {
}
