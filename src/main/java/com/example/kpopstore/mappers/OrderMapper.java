package com.example.kpopstore.mappers;

import com.example.kpopstore.DTO.OrderDTO;
import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.OrderItem;
import com.example.kpopstore.entities.User;


import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }

        List<String> itemIds = order.getItems().stream()
                .map(orderItem -> orderItem.getAlbum().getId().toString())
                .collect(Collectors.toList());

        return new OrderDTO(
                order.getId().toString(),
                order.getUser().getId().toString(),
                itemIds,
                order.getTotalPrice(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

    public static Order toEntity(OrderDTO orderDTO, User user, List<OrderItem> items) {
        if (orderDTO == null) {
            return null;
        }


        return new Order(
                user,
                items,
                orderDTO.getTotalPrice(),
                orderDTO.getStatus(),
                orderDTO.getCreatedAt(),
                orderDTO.getUpdatedAt()
        );
    }
}

