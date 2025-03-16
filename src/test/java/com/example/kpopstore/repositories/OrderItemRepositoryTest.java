package com.example.kpopstore.repositories;

import com.example.kpopstore.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import com.example.kpopstore.entities.Order.OrderStatus;
import com.example.kpopstore.entities.User.Role;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Test
    void testSaveAndFindOrderItem() {
        // Создаем заказ
        Order order = new Order(new User("impala67", "dean.winchester@gmail.com", "password123", Role.USER),
                List.of(), BigDecimal.valueOf(41.98), OrderStatus.PENDING, null, null);
        order = orderRepository.save(order);

        // Создаем альбом
        Album album = new Album("HYYH", "BTS", LocalDate.of(2016, 5, 2),
                BigDecimal.valueOf(25.99), 50, "1st SPECIAL ALBUM", "cover2.jpg");
        album = albumRepository.save(album);

        // Создаем OrderItem
        OrderItem orderItem = new OrderItem(order, album, 2);
        orderItem = orderItemRepository.save(orderItem);

        Optional<OrderItem> foundOrderItem = orderItemRepository.findById(orderItem.getId());

        assertThat(foundOrderItem).isPresent();
        assertThat(foundOrderItem.get().getQuantity()).isEqualTo(2);
    }
}

