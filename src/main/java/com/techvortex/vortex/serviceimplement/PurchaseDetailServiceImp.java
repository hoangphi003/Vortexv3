package com.techvortex.vortex.serviceimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.repository.OrderDao;
import com.techvortex.vortex.service.PurchaseDetailService;

@Service
public class PurchaseDetailServiceImp implements PurchaseDetailService {

    @Autowired
    OrderDao orderDao;

    @Override
    public Order findByOrderIdbyUserName(String username,Integer id) {
        return orderDao.findByOrderByUserName(username,id);
    }
}
