package com.techvortex.vortex.serviceimplement;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.techvortex.vortex.entity.Material;
import com.techvortex.vortex.repository.MaterialDAO;
import com.techvortex.vortex.service.MaterialService;

import javax.transaction.Transactional;

@Service
public class MaterialServiceImpl  implements MaterialService {
    
    @Autowired
    MaterialDAO materialDAO;

    @Override
    public List<Material> findAll() {     
        return materialDAO.findAll();
    }

    @Override
    @Transactional
    public Material findById(Integer MaterialId) {  
        return materialDAO.findById(MaterialId).get();
    }

    @Override
    public Material create(Material material) {
        return materialDAO.save(material);
    }

    @Override
    public Material update(Material material) {
        return materialDAO.save(material);
    }

    @Override
    public void delete(Material material) {
        materialDAO.delete(material);
    }
    
    @Override
	public boolean existsByNameIgnoreCase(String materialName) {
	    return materialDAO.existsByNameIgnoreCase(materialName) > 0;
	}

      
}
