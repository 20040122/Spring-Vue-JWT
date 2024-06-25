package com.project.springboot_jwt.Services;

import com.project.springboot_jwt.Enitity.Product;
import com.project.springboot_jwt.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public String getPhotoByProductId(int productId) {
        Product product = productRepository.findByProductId(productId);
        return product.getPhoto();
    }
}
