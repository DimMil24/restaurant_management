package com.dimitris.restaurant_management.repositories;

import com.dimitris.restaurant_management.projections.CustomerOrderSelectI;
import com.dimitris.restaurant_management.entities.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder,Long> {

    @Query(value = "SELECT product.name,order_product.price,order_product.quantity\n" +
            "FROM customer_order\n" +
            "INNER JOIN order_product \n" +
            "\ton customer_order.id=order_product.order_id\n" +
            "INNER JOIN product \n" +
            "\ton order_product.product_id=product.id\n" +
            "WHERE customer_order.id= :id",nativeQuery = true)
    CustomerOrderSelectI findSpecificOrder(@Param("id") Long id);
    List<CustomerOrder> findAllByRestaurant_Id(UUID restaurantId);
}
