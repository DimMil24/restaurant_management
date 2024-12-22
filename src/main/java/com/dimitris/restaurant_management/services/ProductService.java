package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.Category;
import com.dimitris.restaurant_management.entities.Product;
import com.dimitris.restaurant_management.entities.Restaurant;
import com.dimitris.restaurant_management.entities.requests.ProductRequest;
import com.dimitris.restaurant_management.repositories.ProductRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @PostFilter("filterObject.restaurant.user.username == authentication.name")
    public List<Product> getAllProducts(UUID restaurantId) {
        return productRepository.findByRestaurantId(restaurantId);
    }

    @PostAuthorize("returnObject.restaurant.user.username == authentication.name")
    public Product findProduct(UUID restaurant_id, Long productId) {
        return  productRepository.findByRestaurant_IdAndId(restaurant_id, productId).orElseThrow(
                () -> new RuntimeException("Product not found")
        );
    }

    @Transactional
    @PostAuthorize("returnObject.restaurant.user.username == authentication.name")
    public Product generateProduct(ProductRequest productRequest, Restaurant restaurant) {
        var category = categoryService.findCategoryByNameAndRestaurant(productRequest.category(),
                                                                        restaurant.getId());
        if (category.isPresent()) {
            return new Product(productRequest.name(), BigDecimal.valueOf(productRequest.price()),
                    category.get(),true, productRequest.description(), restaurant);
        } else {
            Category newCategory = categoryService.createCategory(productRequest.category(),restaurant);
            return new Product(productRequest.name(), BigDecimal.valueOf(productRequest.price()), newCategory,
                    true, productRequest.description(), restaurant);
        }
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public boolean productExists(String productName,UUID restaurantId) {
        return productRepository.findByNameAndRestaurant_Id(productName,restaurantId).isPresent();
    }

    public Product findPublicProduct(UUID restaurant_id, Long productId) {
        return  productRepository.findByRestaurant_IdAndId(restaurant_id, productId).orElseThrow(
                () -> new RuntimeException("Product not found")
        );
    }
}
