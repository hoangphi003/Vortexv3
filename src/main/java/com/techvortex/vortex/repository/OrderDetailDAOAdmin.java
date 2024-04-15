package com.techvortex.vortex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.techvortex.vortex.entity.OrderDetail;
import org.springframework.data.domain.Pageable;

@Repository
public interface OrderDetailDAOAdmin extends JpaRepository<OrderDetail,Integer> {
    
}
