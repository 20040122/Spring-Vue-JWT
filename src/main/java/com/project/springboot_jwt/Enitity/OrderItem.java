package com.project.springboot_jwt.Enitity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(generator = "increment")
    private int orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_Id", nullable = false)
    private Orders order; // 这里建立了与 Orders 实体类的关联

    @Column(nullable = false)
    private int productId;

    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private float sumPrice;

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(float sumPrice) {
        this.sumPrice = sumPrice;
    }
}


