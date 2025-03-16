package com.example.kpopstore.repositories;

import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUser(User user);
}
