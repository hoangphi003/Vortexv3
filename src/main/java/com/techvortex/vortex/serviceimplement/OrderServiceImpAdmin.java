package com.techvortex.vortex.serviceimplement;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.OrderDetail;
import com.techvortex.vortex.repository.OrderDao;
import com.techvortex.vortex.repository.OrderDaoAdmin;
import com.techvortex.vortex.service.OrderServiceAdmin;

@Service
public class OrderServiceImpAdmin implements OrderServiceAdmin {
    @Autowired
    OrderDaoAdmin orderDao;

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
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    @Transactional
    public Order findById(Integer OrderId) {
        return orderDao.findById(OrderId).get();
    }

    @Override
    public Order create(Order order) {
        return orderDao.save(order);
    }

    @Override
    public Order update(Order order) {
        return orderDao.save(order);
    }

    @Override
    public void delete(Order order) {
        orderDao.delete(order);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateOrderStatus(Integer orderId, String newStatus) {
        Optional<Order> optionalOrder = orderDao.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderStatus(newStatus);
            orderDao.save(order);
            return ResponseEntity.ok("Đổi trạng thái thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy đơn hàng");
        }
    }

    @Override
    public List<Order> findOrdersByMonth(int month, int year) {
        return orderDao.findOrdersByMonth(month, year);
    }

    @Override
    public long findTotalOrdersByMonth(int month, int year) {
        return orderDao.countOrdersByMonth(month, year);
    }

    @Override
    public List<Long> countOrdersByMonthInYear(int year) {
        List<Long> result = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            Long count = orderDao.countOrdersByMonthInYear(year, month);
            result.add(count != null ? count : 0L);
        }

        return result;
    }

    // biểu đồ tròn

    // điếm số lượng đơn hàng theo từng trạng thái
    // Trong OrderServiceImp
    @Override
    public long countOrdersByStatus(String status) {
        return orderDao.countOrdersByStatus(status);
    }

    @Override
    public long countPendingPaymentOrders() {
        return orderDao.countOrdersByStatus("Chờ thanh toán");
    }

    @Override
    public long countShippingOrders() {
        return orderDao.countOrdersByStatus("Vận chuyển");
    }

    @Override
    public long countDeliveringOrders() {
        return orderDao.countOrdersByStatus("Chờ giao hàng");
    }

    @Override
    public long countCompletedOrders() {
        return orderDao.countOrdersByStatus("Hoàn thành");
    }

    @Override
    public long countCancelledOrders() {
        return orderDao.countOrdersByStatus("Đã hủy");
    }

    // tổng đơn hàng
    @Override
    public int getTotalOrders() {
        List<Order> allOrders = orderDao.findAll();
        return allOrders.size();
    }

    // biểu đồ tròn
    @Override
    public List<Long> countOrdersByYear() {
        List<Long> result = new ArrayList<>();

        // Lấy danh sách các năm từ cơ sở dữ liệu
        List<Integer> years = orderDao.findDistinctYears();

        // Đếm số đơn hàng cho mỗi năm và thêm vào danh sách kết quả
        for (Integer year : years) {
            Long count = orderDao.countOrdersByYear(year);
            result.add(count != null ? count : 0L);
        }

        return result;
    }

    // đếm số năm trong cơ sở dữ liệu
    @Override
    public List<Integer> findDistinctYears() {
        return orderDao.findDistinctYears();
    }

    // Trong OrderServiceImpAdmin
    @Override
    public List<Float> calculateMonthlyRevenueInCurrentYear() {
        // Lấy năm hiện tại
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Khởi tạo danh sách để lưu tổng doanh thu của từng tháng
        List<Float> monthlyRevenue = new ArrayList<>();

        // Tính tổng doanh thu của từng tháng trong năm hiện tại
        for (int month = 1; month <= 12; month++) {
            // Lấy danh sách các đơn hàng trong tháng của năm hiện tại
            List<Order> orders = orderDao.findOrdersByMonth(month, currentYear);

            // Tính tổng doanh thu của từng đơn hàng trong tháng và cộng lại để tính tổng
            // doanh thu của tháng đó
            float totalRevenueOfMonth = 0;
            for (Order order : orders) {
                for (OrderDetail orderDetail : order.getOrderDetails()) {
                    if (orderDetail.getTotal() != null) {
                        totalRevenueOfMonth += orderDetail.getTotal();
                    }
                }
            }
            monthlyRevenue.add(totalRevenueOfMonth);
        }

        return monthlyRevenue;
    }

}
