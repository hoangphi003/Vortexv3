package com.techvortex.vortex.service;

import com.techvortex.vortex.entity.Order;

public interface PurchaseDetailService {

    Order findByOrderIdbyUserName(String username,Integer id);

}
