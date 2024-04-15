package com.techvortex.vortex.serviceimplement;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.techvortex.vortex.entity.Color;
import com.techvortex.vortex.repository.ColorDAO;
import com.techvortex.vortex.service.ColorService;

import java.util.List;
import javax.transaction.Transactional;

@Service
public class ColorServiceImpl implements ColorService{
    
    @Autowired
    ColorDAO colorDAO;

    @Override
    public List<Color> findAll() {
        return colorDAO.findAll();
    }

    @Override
    @Transactional
    public Color findById(Integer ColorId) {
        return colorDAO.findById(ColorId).get();
    }

    @Override
    public Color create(Color color) {
        return colorDAO.save(color);
    }

    @Override
    public Color update(Color color) {
        return colorDAO.save(color);
    }

    @Override
    public void delete(Color color) {
        colorDAO.delete(color);
    }

    @Override
    public boolean existsByNameIgnoreCase(String colorName) {
	    return colorDAO.existsByNameIgnoreCase(colorName) > 0;
	}
    
}
