package com.project.springboot_jwt.Services;

import com.project.springboot_jwt.Enitity.Orders;
import com.project.springboot_jwt.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Orders> getOrderByUserId(int userId) {
        return orderRepository.findByUserId(userId);
    }


    public boolean createOrder(Orders order) {
        try {
            orderRepository.save(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
