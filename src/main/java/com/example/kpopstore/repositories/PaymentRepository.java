package com.example.kpopstore.repositories;

import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByOrder(Order order);
}
