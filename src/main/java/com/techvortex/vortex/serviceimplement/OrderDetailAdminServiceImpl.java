package com.techvortex.vortex.serviceimplement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import com.techvortex.vortex.entity.OrderDetail;
import com.techvortex.vortex.entity.ProductDetail;
import com.techvortex.vortex.repository.OrderDetailDAOAdmin;
import com.techvortex.vortex.service.BrandService;
import com.techvortex.vortex.service.OrderDetailServiceAdmin;

@Service
public class OrderDetailAdminServiceImpl implements OrderDetailServiceAdmin {

    @Autowired
    OrderDetailDAOAdmin dao;

    @Override
    public List<OrderDetail> findAll() {
        return dao.findAll();
    }

    @Override
    @Transactional
    public OrderDetail findById(Integer OrderDetailId) {
        return dao.findById(OrderDetailId).get();
    }

    @Override
    public OrderDetail create(OrderDetail detail) {
        return dao.save(detail);
    }

    @Override
    public OrderDetail update(OrderDetail detail) {
        return dao.save(detail);
    }

    @Override
    public void delete(OrderDetail detail) {
        dao.delete(detail);
    }

    @Override
    public Float calculateTotalRevenue() {
        List<OrderDetail> orderDetails = dao.findAll();
        Float totalRevenue = 0.0f;

        for (OrderDetail detail : orderDetails) {
            if (detail.getTotal() != null) {
                totalRevenue += detail.getTotal();
            }
        }

        return totalRevenue; // tổng doanh thu của shop
    }

    @Override
    public Integer calculateTotalProductsSold() {
        List<OrderDetail> orderDetails = dao.findAll();
        int totalProductsSold = 0;

        for (OrderDetail detail : orderDetails) {
            if (detail.getQuantity() != null) {
                totalProductsSold += detail.getQuantity();
            }
        }

        return totalProductsSold;
    } // tổng sản phẩm bán được

    @Override
    public Integer calculateTotalOrdersSold() {
        List<OrderDetail> orderDetails = dao.findAll();
        Set<Integer> uniqueOrderDetailIds = new HashSet<>();

        for (OrderDetail detail : orderDetails) {
            uniqueOrderDetailIds.add(detail.getOrderDetailId());
        }

        return uniqueOrderDetailIds.size();
    } // tổng đơn hàng bán được

    
}