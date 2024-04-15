package com.techvortex.vortex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techvortex.vortex.entity.Brand;

public interface BrandDao extends JpaRepository<Brand,Integer>{
    
}
