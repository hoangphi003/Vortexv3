package com.techvortex.vortex.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.techvortex.vortex.entity.Order;

public interface OrderDaoAdmin extends JpaRepository<Order, Integer> {

    @Query("select o from Order o where o.account.UserName = :name")
    List<Order> findOrderByUsername(@Param("name") String userName);

    @Query("select o from Order o where o.OrderId = :id")
    Order findOrderById(@Param("id") Integer id);

    // trạng thái đơn hàng
    @Query("select o from Order o where o.account.UserName = :name and o.OrderStatus like :status")
    List<Order> findOrderByStatus(@Param("name") String userName, @Param("status") String keyword);

    // thống kê biểu đồ cột
    @Query("SELECT o FROM Order o WHERE MONTH(o.OrderDate) = :month AND YEAR(o.OrderDate) = :year")
    List<Order> findOrdersByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COUNT(o) FROM Order o WHERE MONTH(o.OrderDate) = :month AND YEAR(o.OrderDate) = :year")
    long countOrdersByMonth(@Param("month") int month, @Param("year") int year);

    // Trong OrderDao
    @Query("SELECT COUNT(o) FROM Order o WHERE YEAR(o.OrderDate) = :year AND MONTH(o.OrderDate) = :month")
    Long countOrdersByMonthInYear(@Param("year") int year, @Param("month") int month);

    // biểu đồ cột 

    // điếm số lượng đơn hàng theo trạng thái
    @Query("SELECT COUNT(o) FROM Order o WHERE o.OrderStatus = :status")
    long countOrdersByStatus(@Param("status") String status);

    // biểu đồ tròn 
    // Phương thức để lấy tổng số đơn hàng theo từng năm
    @Query("SELECT DISTINCT YEAR(o.OrderDate) FROM Order o")
    List<Integer> findDistinctYears();

    @Query("SELECT COUNT(o) FROM Order o WHERE YEAR(o.OrderDate) = :year")
    long countOrdersByYear(@Param("year") Integer year);
    
      // select option orderdate
      @Query("SELECT o FROM Order o WHERE o.OrderDate = :targetDate")
      List<Order> findOrdersByDate(@Param("targetDate") Date targetDate);
}
