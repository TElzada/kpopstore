package com.example.kpopstore.mappers;

import com.example.kpopstore.DTO.OrderItemDTO;
import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.OrderItem;
import com.example.kpopstore.entities.Album;

public class OrderItemMapper {

    public static OrderItemDTO toDTO(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        return new OrderItemDTO(
                orderItem.getOrder().getId().toString(),
                orderItem.getAlbum().getId().toString(),
                orderItem.getQuantity()
        );
    }

    public static OrderItem toEntity(OrderItemDTO orderItemDTO, Order order, Album album) {
        if (orderItemDTO == null) {
            return null;
        }

        return new OrderItem(
                order,
                album,
                orderItemDTO.getQuantity()
        );
    }
}
