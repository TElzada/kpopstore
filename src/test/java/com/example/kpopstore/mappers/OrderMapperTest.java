package com.example.kpopstore.mappers;

import com.example.kpopstore.DTO.OrderDTO;
import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.Order.OrderStatus;
import com.example.kpopstore.entities.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

class OrderMapperTest {

    @Test
    void testToDTO() {
        User user = new User("testUser", "test@example.com", "password123", User.Role.USER);
        Order order = new Order(user, Arrays.asList(), BigDecimal.valueOf(41.98), OrderStatus.PENDING, LocalDateTime.now(), LocalDateTime.now());
        order.setId(UUID.randomUUID());

        OrderDTO orderDTO = OrderMapper.toDTO(order);

        assertNotNull(orderDTO);
        assertEquals(order.getId(), orderDTO.getId());
        assertEquals(order.getTotalPrice(), orderDTO.getTotalPrice());
        assertEquals(order.getStatus().toString(), orderDTO.getStatus());
    }

    @Test
    void testToEntity() {
        String id = UUID.randomUUID().toString();

        OrderDTO orderDTO = new OrderDTO(
                id,
                UUID.randomUUID().toString(),
                List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString()),
                BigDecimal.valueOf(100.99),
                OrderStatus.PENDING,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        Order order = OrderMapper.toEntity(orderDTO, new User(), new ArrayList<>());

        assertNotNull(order);
        assertEquals(orderDTO.getTotalPrice(), order.getTotalPrice());
        assertEquals(orderDTO.getStatus(), order.getStatus().toString());
        assertEquals(orderDTO.getCreatedAt(), order.getCreatedAt());
    }

}

