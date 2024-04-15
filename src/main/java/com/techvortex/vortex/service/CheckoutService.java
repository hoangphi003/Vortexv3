package com.techvortex.vortex.service;

import com.techvortex.vortex.entity.Discount;
import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.OrderDetail;
import com.techvortex.vortex.entity.OrderDiscount;

public interface CheckoutService {
    Order save(Order order);

    void saveOrderDetail(OrderDetail orderDetail);

    Discount getDiscountCode(String code);

    Discount findDiscountId(Integer id);

    void saveDiscount(Discount discount);

    void saveOrderDiscount(OrderDiscount orderDiscount);

}
