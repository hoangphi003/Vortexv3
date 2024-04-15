package com.techvortex.vortex.serviceimplement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import com.techvortex.vortex.entity.Product;
import com.techvortex.vortex.repository.ProductDAO;
import com.techvortex.vortex.service.ProductService;

@Service
public class ProductImpl implements ProductService {
      
    @Autowired
    ProductDAO productDAO;

    @Override
    public List<Product> findAll() {       
        return productDAO.findAll();
    }

    @Override
    @Transactional
    public Product findById(Integer ProductId) {
        return productDAO.findById(ProductId).get();
    }

    @Override
    public Product create(Product product) {
        return productDAO.save(product);
    }

    @Override
    public Product update(Product product) {
        return productDAO.save(product);
    }

    @Override
    public void delete(Product product) {
        productDAO.delete(product);
    }

    @Override
	public boolean existsByNameIgnoreCase(String productName) {
	    return productDAO.existsByNameIgnoreCase(productName) > 0;
	}

}
