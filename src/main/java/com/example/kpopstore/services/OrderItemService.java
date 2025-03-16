package com.example.kpopstore.services;

import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.OrderItem;
import com.example.kpopstore.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }


    public OrderItem addOrderItem(Order order, OrderItem orderItem) {
        orderItem.setOrder(order);
        return orderItemRepository.save(orderItem);
    }


    public List<OrderItem> getOrderItemsByOrderId(UUID orderId) {
        return orderItemRepository.findAll().stream()
                .filter(item -> item.getOrder().getId().equals(orderId))
                .toList();
    }

    public OrderItem updateOrderItemQuantity(UUID orderItemId, int newQuantity) {
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(orderItemId);
        if (orderItemOptional.isPresent()) {
            OrderItem orderItem = orderItemOptional.get();
            orderItem.setQuantity(newQuantity);
            return orderItemRepository.save(orderItem);
        }
        throw new RuntimeException("OrderItem not found with ID: " + orderItemId);
    }

    public void deleteOrderItem(UUID orderItemId) {
        if (orderItemRepository.existsById(orderItemId)) {
            orderItemRepository.deleteById(orderItemId);
        } else {
            throw new RuntimeException("OrderItem not found with ID: " + orderItemId);
        }
    }
}

