package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Product;
import com.dimitris.restaurant_management.entities.ProductCategory;
import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.projections.ProductSelectI;
import com.dimitris.restaurant_management.repositories.ProductRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
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

    @PostFilter("filterObject.restaurant.user.username == authentication.name")
    public Collection<ProductSelectI> findAllFiltered() {
        return productRepository.findAllFiltered();
    }

    @PostAuthorize("returnObject.restaurant.user.username == authentication.name")
    public Product generateProduct(String name, Double price, ProductCategory productCategory, Restaurant restaurant) {
        return new Product(name, BigDecimal.valueOf(price),productCategory,true,null,restaurant);
    }

    public void addProduct(String name, Double price, ProductCategory productCategory, Restaurant restaurant) {
        Product pr = generateProduct(name,price,productCategory,restaurant);
        productRepository.save(pr);
    }

    public void deleteProduct(UUID restaurant_id,Long id) {
        Product product = findProduct(restaurant_id,id);
        productRepository.delete(product);
    }
}
