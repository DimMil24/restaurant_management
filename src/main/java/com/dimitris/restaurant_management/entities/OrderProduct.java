package com.dimitris.restaurant_management.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class OrderProduct {

    @Id @GeneratedValue
    private Long Id;

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private CustomerOrder customerOrder;

    private Long quantity;
    private BigDecimal Price;

    public OrderProduct(Long id, Product product, CustomerOrder customerOrder, Long quantity, BigDecimal price) {
        Id = id;
        this.product = product;
        this.customerOrder = customerOrder;
        this.quantity = quantity;
        Price = price;
    }

    public OrderProduct() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }
}
