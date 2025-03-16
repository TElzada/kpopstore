package com.example.kpopstore.repositories;

import com.example.kpopstore.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
