package com.techvortex.vortex.service;
import java.util.List;
import com.techvortex.vortex.entity.Material;

public interface MaterialService {
    
    public List<Material> findAll(); // in ra lưu vào danh sách

    public Material findById(Integer MaterialId); // tìm kiếm theo id 

   public Material create(Material material); // thêm danh sách

   public Material update(Material material); // sửa danh sách

   public void delete(Material material);
   
   public boolean existsByNameIgnoreCase(String MaterialName);

   
}
