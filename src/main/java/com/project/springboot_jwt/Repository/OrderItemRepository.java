package com.project.springboot_jwt.Repository;

import com.project.springboot_jwt.Enitity.OrderItem;
import com.project.springboot_jwt.Enitity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    OrderItem findByOrderItemId(int orderItemId);
    OrderItem findByProductId(int productId);
    List<OrderItem> findByOrder(Orders order);
    @Query("SELECT new map(p.productId as productId, p.productName as productName, oi.order.orderId as orderId, oi.quantity as quantity, oi.sumPrice as sumPrice) " +
            "FROM Product p JOIN OrderItem oi " +
            "WHERE p.productId = oi.productId AND oi.order.orderId = :orderId")
    List<Map<String, Object>> getProductsWithOrderItems(int orderId);
}
