package com.example.kpopstore.mappers;

import com.example.kpopstore.DTO.OrderItemDTO;
import com.example.kpopstore.entities.OrderItem;
import com.example.kpopstore.entities.Album;
import com.example.kpopstore.entities.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class OrderItemMapperTest {

    @Test
    void testToDTO() {
        OrderItem orderItem = new OrderItem(new Order(), new Album(), 2);
        orderItem.setId(UUID.randomUUID());

        OrderItemDTO orderItemDTO = OrderItemMapper.toDTO(orderItem);

        assertNotNull(orderItemDTO);
        assertEquals(orderItem.getQuantity(), orderItemDTO.getQuantity());
    }

    @Test
    void testToEntity() {
        String orderId = UUID.randomUUID().toString();
        String albumId = UUID.randomUUID().toString();

        OrderItemDTO orderItemDTO = new OrderItemDTO(orderId, albumId, 2);

        OrderItem orderItem = OrderItemMapper.toEntity(orderItemDTO, new Order(), new Album());

        assertNotNull(orderItem);
        assertEquals(orderItemDTO.getQuantity(), orderItem.getQuantity());
    }


}
