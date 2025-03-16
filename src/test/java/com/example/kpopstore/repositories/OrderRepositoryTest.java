package com.example.kpopstore.repositories;

import com.example.kpopstore.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.example.kpopstore.entities.Order.OrderStatus;
import com.example.kpopstore.entities.User.Role;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    void testSaveAndFindOrder() {
        // Создаем пользователя для заказа
        User user = new User("impala67", "dean.winchester@gmail.com", "password123", Role.USER);
        user = userRepository.save(user);

        // Создаем альбом для OrderItem
        Album album = new Album("Wings", "BTS", LocalDate.of(2016, 10, 10),
                BigDecimal.valueOf(20.99), 100, "2nd FULL-LENGTH ALBUM", "cover1.jpg");

        // Создаем OrderItem для заказа
        OrderItem orderItem = new OrderItem(null, album, 2); // Передаем заказ позже
        orderItem = orderItemRepository.save(orderItem);

        // Создаем заказ
        Order order = new Order(user, List.of(orderItem), BigDecimal.valueOf(41.98), OrderStatus.PENDING,
                LocalDateTime.of(2025, 2, 27, 12, 59), LocalDateTime.of(2025, 2, 27, 13, 30));
        order = orderRepository.save(order);

        // Обновляем OrderItem с правильным order
        orderItem.setOrder(order);
        orderItemRepository.save(orderItem);

        Optional<Order> foundOrder = orderRepository.findById(order.getId());

        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getUser().getUsername()).isEqualTo("impala67");
        assertThat(foundOrder.get().getTotalPrice()).isEqualTo(BigDecimal.valueOf(41.98));
    }
}

