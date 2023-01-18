package com.group11.fooddelivery.repository;

import com.group11.fooddelivery.model.OrdersByUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrdersByUsersRepository extends JpaRepository<OrdersByUser, String> {
      OrdersByUser findByOrderId(String orderId);
}
