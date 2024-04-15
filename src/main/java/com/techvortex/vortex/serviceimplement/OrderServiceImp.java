package com.techvortex.vortex.serviceimplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.OrderDetail;
import com.techvortex.vortex.repository.OrderDao;
import com.techvortex.vortex.service.OrderService;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Override
    public List<Order> findOrderByUsername(String userName) {
        return orderDao.findOrderByUsername(userName);
    }

    @Override
    public Order findOrderById(Integer id) {
        return orderDao.findOrderById(id);
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }

    @Override
    public List<Order> findOrderByStatus(String userName, String keyword) {
        return orderDao.findOrderByStatus(userName, keyword);
    }

    @Override
    public List<OrderDetail> findOrderDetailByOrderId(Integer orderid) {
        return orderDao.findOrderDetail(orderid);
    }
}
