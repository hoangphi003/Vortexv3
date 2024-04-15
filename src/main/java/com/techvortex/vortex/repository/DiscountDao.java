package com.techvortex.vortex.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.techvortex.vortex.entity.Discount;

@Repository
public interface DiscountDao extends JpaRepository<Discount, Integer> {
    
}
