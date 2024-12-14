package com.dimitris.restaurant_management.repositories;

import com.dimitris.restaurant_management.entities.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder,Long> {

    List<CustomerOrder> findAllByRestaurant_Id(UUID restaurantId);
}
