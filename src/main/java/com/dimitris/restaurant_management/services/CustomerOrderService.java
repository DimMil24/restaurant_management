package com.dimitris.restaurant_management.services;

import com.dimitris.restaurant_management.entities.*;
import com.dimitris.restaurant_management.repositories.CustomerOrderRepository;
import com.dimitris.restaurant_management.repositories.ProductRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.FindException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final ProductRepository productRepository;

    public CustomerOrderService(CustomerOrderRepository customerOrderRepository, ProductRepository productRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.productRepository = productRepository;
    }

    @PostFilter("filterObject.user.username == authentication.name")
    public List<CustomerOrder> getAllOrders() {
        return customerOrderRepository.findAll();
    }

    @PostAuthorize("returnObject.user.username == authentication.name")
    public CustomerOrder findOrder(Long id) {
        return customerOrderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Customer Order Not Found")
        );
    }

    @Transactional
    public void AddOrder(List<Long> products, List<Long> quantity, Restaurant restaurant, User user) {
        CustomerOrder order = new CustomerOrder();
        order.setRestaurant(restaurant);
        order.setDate(LocalDateTime.now());
        order.setUser(user);
        List<OrderProduct> p = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            Product prod = productRepository.findById(products.get(i))
                    .orElseThrow(() -> new RuntimeException("Product Not found"));

            p.add(new OrderProduct(null,prod,order,quantity.get(i),prod.getPrice()));
        }
        order.setProduct(p);
        customerOrderRepository.save(order);
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
}
