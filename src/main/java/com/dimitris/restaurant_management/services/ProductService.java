package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Product;
import com.dimitris.restaurant_management.entities.ProductCategory;
import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.repositories.ProductRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostFilter("filterObject.restaurant.user.username == authentication.name")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostAuthorize("returnObject.restaurant.user.username == authentication.name")
    public Product findProduct(UUID restaurant_id, Long productId) {
        return  productRepository.findByRestaurant_IdAndId(restaurant_id, productId).orElseThrow(
                () -> new RuntimeException("Product not found")
        );
    }

    @PostAuthorize("returnObject.restaurant.user.username == authentication.name")
    public Product generateProduct(String name, Double price, ProductCategory productCategory, String description, Restaurant restaurant) {
        return new Product(name, BigDecimal.valueOf(price),productCategory,true,description,restaurant);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public Product findPublicProduct(UUID restaurant_id, Long productId) {
        return  productRepository.findByRestaurant_IdAndId(restaurant_id, productId).orElseThrow(
                () -> new RuntimeException("Product not found")
        );
    }
}
