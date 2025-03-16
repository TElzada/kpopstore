package com.example.kpopstore.repositories;
import com.example.kpopstore.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

}
