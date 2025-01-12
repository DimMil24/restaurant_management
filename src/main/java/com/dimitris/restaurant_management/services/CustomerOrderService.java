package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.*;
import com.dimitris.restaurant_management.entities.requests.NewOrderRequest;
import com.dimitris.restaurant_management.repositories.CustomerOrderRepository;
import com.dimitris.restaurant_management.repositories.ProductRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.FindException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final ProductRepository productRepository;
    private final RestaurantService restaurantService;

    public CustomerOrderService(CustomerOrderRepository customerOrderRepository, ProductRepository productRepository, RestaurantService restaurantService) {
        this.customerOrderRepository = customerOrderRepository;
        this.productRepository = productRepository;
        this.restaurantService = restaurantService;
    }

    @PostFilter("filterObject.restaurant.user.username == authentication.name")
    public List<CustomerOrder> getAllOrders(UUID RestaurantId) {
        return customerOrderRepository.findAllByRestaurant_Id(RestaurantId);
    }

    @PostAuthorize("returnObject.restaurant.user.username == authentication.name")
    public CustomerOrder findOrder(Long id) {
        return customerOrderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Customer Order Not Found"));
    }

    @Transactional
    public void DeleteOrder(Long id) {
        CustomerOrder order = customerOrderRepository.findById(id)
                .orElseThrow(() -> new FindException("Order Not Found"));
        customerOrderRepository.delete(order);
    }

    @Transactional
    public void CompleteOrder(Long id) {
        CustomerOrder order = customerOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
        if (order.getOpen()) {
            order.setOpen(false);
            customerOrderRepository.save(order);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','OWNER')")
    @Transactional
    public void AddUserOrder(NewOrderRequest newOrderRequests, User user) {
        CustomerOrder order = new CustomerOrder();
        Restaurant restaurant = restaurantService.getRestaurantById(newOrderRequests.restaurantId());
        order.setRestaurant(restaurant);
        order.setDate(LocalDateTime.now());
        order.setUser(user);
        List<OrderProduct> p = new ArrayList<>();
        for (int i = 0; i < newOrderRequests.itemQuantity().size(); i++) {
            Product prod = productRepository.findById(newOrderRequests.itemQuantity().get(i).id())
                    .orElseThrow(() -> new RuntimeException("Product Not found"));
            var quantity = newOrderRequests.itemQuantity().get(i).quantity();
            BigDecimal price = new BigDecimal(quantity).multiply(prod.getPrice());
            p.add(new OrderProduct(null,prod,order,quantity,price));
        }
        order.setProduct(p);
        customerOrderRepository.save(order);
    }
}
