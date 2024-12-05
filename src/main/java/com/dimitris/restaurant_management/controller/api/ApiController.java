package com.dimitris.restaurant_management.controller.api;

import com.dimitris.restaurant_management.entities.Product;
import com.dimitris.restaurant_management.entities.responses.ProductResponse;
import com.dimitris.restaurant_management.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private final ProductService productService;

    public ApiController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{restaurant_id}/{product_id}")
    ResponseEntity<ProductResponse> getProduct(@PathVariable UUID restaurant_id, @PathVariable Long product_id) {
        Product product = productService.findPublicProduct(restaurant_id, product_id);
        ProductResponse productResponse = new ProductResponse(product.getName(),product.getPrice(),
                product.getCategory().name(),product.getDescription());
        return ResponseEntity.ok(productResponse);
    }
}
