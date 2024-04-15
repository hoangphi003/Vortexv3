package com.techvortex.vortex.service;

import java.util.List;

import com.techvortex.vortex.entity.OrderDetail;

public interface OrderDetailService {

    OrderDetail findById(Integer id);

    void save(OrderDetail orderDetail);

}
