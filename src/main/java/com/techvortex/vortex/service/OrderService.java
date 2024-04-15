package com.techvortex.vortex.service;

import java.util.List;

import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.OrderDetail;

public interface OrderService {
    List<Order> findOrderByUsername(String userName);

    Order findOrderById(Integer id);

    void save(Order order);

    List<Order> findOrderByStatus(String id, String keyword);

    List<OrderDetail> findOrderDetailByOrderId(Integer orderid);

}
