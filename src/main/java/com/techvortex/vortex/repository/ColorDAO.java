package com.techvortex.vortex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvortex.vortex.entity.Color;

public interface ColorDAO extends JpaRepository<Color, Integer> {

    @Query("SELECT COUNT(c) FROM Color c WHERE LOWER(c.ColorName) = LOWER(:colorName)")
    int existsByNameIgnoreCase(@Param("colorName") String colorName);

}
