package com.techvortex.vortex.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.techvortex.vortex.entity.Material;

@Repository
public interface MaterialDAO extends JpaRepository<Material, Integer> {
  // Trong CategoryDAO
  @Query("SELECT COUNT(c) FROM Material c WHERE LOWER(c.MaterialName) = LOWER(:materialName)")
  int existsByNameIgnoreCase(@Param("materialName") String materialName);

   
}
