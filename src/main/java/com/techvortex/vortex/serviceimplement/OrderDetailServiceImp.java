package com.techvortex.vortex.serviceimplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.OrderDetail;
import com.techvortex.vortex.repository.OrderDetailDao;
import com.techvortex.vortex.service.OrderDetailService;

@Service
public class OrderDetailServiceImp implements OrderDetailService {
    @Autowired
    OrderDetailDao orderDetailDao;

    @Override
    public OrderDetail findById(Integer id) {
        return orderDetailDao.findById(id).get();
    }

    @Override
    public void save(OrderDetail orderDetail) {
        orderDetailDao.save(orderDetail);
    }

}
