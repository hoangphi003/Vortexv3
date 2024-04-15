package com.techvortex.vortex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvortex.vortex.entity.OrderDetail;

public interface OrderDetailDao extends JpaRepository<OrderDetail, Integer> {

}
