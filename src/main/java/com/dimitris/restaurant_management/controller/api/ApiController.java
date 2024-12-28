package com.dimitris.restaurant_management.controller.api;

import com.dimitris.restaurant_management.entities.Product;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.entities.requests.NewOrderRequest;
import com.dimitris.restaurant_management.entities.responses.ProductResponse;
import com.dimitris.restaurant_management.services.CustomerOrderService;
import com.dimitris.restaurant_management.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private final ProductService productService;
    private final CustomerOrderService customerOrderService;

    public ApiController(ProductService productService, CustomerOrderService customerOrderService) {
        this.productService = productService;
        this.customerOrderService = customerOrderService;
    }

    @GetMapping("/product/{restaurant_id}/{product_id}")
    ResponseEntity<ProductResponse> getProduct(@PathVariable UUID restaurant_id, @PathVariable Long product_id) {
        Product product = productService.findPublicProduct(restaurant_id, product_id);
        ProductResponse productResponse = new ProductResponse(product.getName(),product.getPrice(),
                product.getCategory().getName(),product.getDescription());
        return ResponseEntity.ok(productResponse);
    }
//TODO: Implement proper handling of post Order
    @PostMapping("/newOrder")
    public ResponseEntity<String> newOrder(@RequestBody @Valid NewOrderRequest newOrderRequest, @AuthenticationPrincipal User user) {
        customerOrderService.AddUserOrder(newOrderRequest,user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
