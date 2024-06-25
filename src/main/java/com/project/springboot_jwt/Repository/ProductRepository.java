package com.project.springboot_jwt.Repository;

import com.project.springboot_jwt.Enitity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);
    Product findByProductId(int productId);
}
