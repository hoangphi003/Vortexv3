package com.techvortex.vortex.serviceimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Discount;
import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.OrderDetail;
import com.techvortex.vortex.entity.OrderDiscount;
import com.techvortex.vortex.repository.CheckoutDao;
import com.techvortex.vortex.repository.DiscountUserDao;
import com.techvortex.vortex.repository.OrderDetailDao;
import com.techvortex.vortex.repository.OrderDiscountDao;
import com.techvortex.vortex.service.CheckoutService;

@Service
public class CheckoutServiceImp implements CheckoutService {

    @Autowired
    DiscountUserDao discountUserDao;

    @Autowired
    CheckoutDao checkoutDao;

    @Autowired
    OrderDetailDao orderDetailDao;

    @Autowired
    OrderDiscountDao orderDiscountDao;

    @Override
    public Order save(Order order) {
        return checkoutDao.save(order);
    }

    @Override
    public void saveOrderDetail(OrderDetail orderDetail) {
        orderDetailDao.save(orderDetail);
    }

    @Override
    public Discount getDiscountCode(String code) {
        return discountUserDao.findAllDiscountCode(code);
    }

    @Override
    public Discount findDiscountId(Integer id) {
        return discountUserDao.findById(id).get();
    }

    @Override
    public void saveDiscount(Discount discount) {
        discountUserDao.save(discount);
    }

    @Override
    public void saveOrderDiscount(OrderDiscount orderDiscount) {
        orderDiscountDao.save(orderDiscount);
    }
}
