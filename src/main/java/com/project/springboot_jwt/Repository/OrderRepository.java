package com.project.springboot_jwt.Repository;

import com.project.springboot_jwt.Enitity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUserId(int userId);
    Orders findByOrderDate(Date orderDate);
    Orders findByUserIdAndOrderDate(int userId, Date orderDate);
    Orders findByOrderId(int orderId);
}
