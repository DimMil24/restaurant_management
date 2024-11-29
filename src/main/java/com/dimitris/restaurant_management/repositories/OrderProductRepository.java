package com.dimitris.restaurant_management.repositories;

import com.dimitris.restaurant_management.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {
}
