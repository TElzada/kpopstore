package com.example.kpopstore.services;

import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.Order.OrderStatus;
import com.example.kpopstore.entities.OrderItem;
import com.example.kpopstore.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public Order createOrder(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    public Order getOrderById(UUID orderId) {

        Optional<Order> orderOpt = orderRepository.findById(orderId);

        return orderOpt.orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
    }


    public Order updateOrderStatus(UUID orderId, OrderStatus newStatus) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(newStatus);
            order.setUpdatedAt(LocalDateTime.now());
            return orderRepository.save(order);
        }
        throw new RuntimeException("Order not found with ID: " + orderId);
    }


    public BigDecimal calculateTotalPrice(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getAlbum().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public Order updateTotalPrice(UUID orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            BigDecimal totalPrice = calculateTotalPrice(order.getItems());
            order.setTotalPrice(totalPrice);
            return orderRepository.save(order);
        }
        throw new RuntimeException("Order not found with ID: " + orderId);
    }

    @Transactional
    public Order updateOrder(UUID orderId, Order updatedOrder) {

        Optional<Order> existingOrderOpt = orderRepository.findById(orderId);
        if (existingOrderOpt.isPresent()) {
            Order existingOrder = existingOrderOpt.get();


            existingOrder.setStatus(updatedOrder.getStatus());
            existingOrder.setTotalPrice(updatedOrder.getTotalPrice());
            existingOrder.setUpdatedAt(updatedOrder.getUpdatedAt());
            existingOrder.setItems(updatedOrder.getItems());

            return orderRepository.save(existingOrder);
        } else {

            throw new RuntimeException("Order not found with id " + orderId);
        }
    }
    @Transactional
    public void deleteOrder(UUID orderId) {

        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {

            orderRepository.deleteById(orderId);
        } else {

            throw new RuntimeException("Order not found with id " + orderId);
        }
    }
}

