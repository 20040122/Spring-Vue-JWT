package com.project.springboot_jwt.Controler;

import com.project.springboot_jwt.Enitity.Product;
import com.project.springboot_jwt.Repository.ProductRepository;
import com.project.springboot_jwt.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    private final Path rootLocation = Paths.get("product_images");

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/createProduct")
    public void createProduct(@RequestParam("image") MultipartFile image,
                              @RequestParam String description,
                              @RequestParam String single_price,
                              @RequestParam String product_name,
                              @RequestParam String kind) {
        try {
            Product product = new Product();
            product.setDescription(description);
            product.setSinglePrice(single_price);
            product.setProductName(product_name);
            product.setKind(kind);
            product = productRepository.save(product);

            // Now product_id is available
            Path destinationFile = rootLocation.resolve(Paths.get(product.getProductId().toString()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new IllegalStateException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            String filename = destinationFile.getFileName().toString();
            product.setPhoto(filename);
            productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Product could not be stored.", e);
        }
    }

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 获取所有商品
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

}
