package com.techvortex.vortex.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.techvortex.vortex.entity.Product;


@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {
    @Query("SELECT COUNT(c) FROM Product c WHERE LOWER(c.ProductName) = LOWER(:productName)")
    int existsByNameIgnoreCase(@Param("productName") String productName);

}
