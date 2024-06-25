package com.project.springboot_jwt.Enitity;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.sql.Date;


@Entity
@Table(name = "Orders")
public class Orders {
    @Id
    @GeneratedValue(generator = "increment")
    private int orderId;
    @Column(nullable = false, unique = true)
    private Integer userId;
    @Column(nullable = false)
    private float totalPrice;
    @Column(nullable = false)
    private Date orderDate;
    @Column(nullable = false)
    private String deliveryAddress;
    @Column(nullable = false)
    private String deliveryStatus;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Orders orElse(Object o) {
        return null;
    }
}

