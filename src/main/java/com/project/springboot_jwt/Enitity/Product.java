package com.project.springboot_jwt.Enitity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId; // 修改为 product_id

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    private String productName; // 修改为 product_name
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    private String description;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    private String singlePrice;

    public String getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(String singlePrice) {
        this.singlePrice = singlePrice;
    }
    private String kind;
    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }


  // 修改为 single_price
    private String photo;
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "{" +
                "\"productId\"" + ":\"" + productId + "\"" +
                ", \"productName\"" +  ":\"" + productName + '\"' +
                ", \"description\"" + ":\"" + description + '\"' +
                ", \"singlePrice\"" + ":\"" + singlePrice + '\"' +
                ", \"kind\"" + ":\"" + kind + '\"' +
                ", \"photo\"" + ":\"" + photo + '\"' +
                '}';
    }
}
