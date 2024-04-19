package com.techvortex.vortex.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;

import com.techvortex.vortex.entity.Order;

public interface OrderServiceAdmin {
    List<Order> findOrderByUsername(String userName);

    Order findOrderById(Integer id);

    void save(Order order);

    List<Order> findOrderByStatus(String userName, String keyword);

    public List<Order> findAll();

    public Order findById(Integer OrderId); // tìm kiếm theo id

    public Order create(Order order); // thêm danh sách

    public Order update(Order order); // sửa danh sách

    public void delete(Order order);

    ResponseEntity<String> updateOrderStatus(Integer orderId, String newStatus); // Thêm phương thức cập nhật trạng thái

    List<Order> findOrdersByMonth(int month, int year);// thống kê biểu đồ cột

    long findTotalOrdersByMonth(int month, int year);

    // Trong OrderService
    List<Long> countOrdersByMonthInYear(int year);

    // biểu đồ tròn

    // điếm số lượng đơn hàng theo từng trạng thái
    long countOrdersByStatus(String status);

    long countPendingPaymentOrders();

    long countShippingOrders();

    long countDeliveringOrders();

    long countCompletedOrders();

    long countCancelledOrders();

    // tổng đơn hàng
    int getTotalOrders();

    // biểu đồ tròn
    // Phương thức để lấy tổng số đơn hàng theo từng năm
    List<Long> countOrdersByYear();

    List<Integer> findDistinctYears(); // đếm số năm trong cơ sở dữ liệu

    // Trong OrderServiceAdmin
    List<Float> calculateMonthlyRevenueInCurrentYear();

    // sleect option orderdate theo ngày
    List<Order> findOrdersByDate(Integer day, Integer month, Integer year);

    // theo tháng
    List<Order> findOrdersByDatee(Integer month, Integer year);
}
