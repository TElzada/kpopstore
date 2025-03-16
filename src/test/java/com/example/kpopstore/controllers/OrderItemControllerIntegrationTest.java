package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.Album;
import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.OrderItem;
import com.example.kpopstore.repositories.AlbumRepository;
import com.example.kpopstore.repositories.OrderItemRepository;
import com.example.kpopstore.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class OrderItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AlbumRepository albumRepository;

    private UUID orderItemId;
    private OrderItem orderItem;
    private Order order;
    private Album album;

    @BeforeEach
    public void setUp() {
        order = new Order();
        order = orderRepository.save(order);

        album = new Album();
        album.setTitle("Test Album");
        album = albumRepository.save(album);

        orderItem = new OrderItem(order, album, 2);
        orderItem = orderItemRepository.save(orderItem);
        orderItemId = orderItem.getId();
    }

    @Test
    public void getOrderItemById_ShouldReturnOrderItem() throws Exception {
        mockMvc.perform(get("/api/order-items/{orderItemId}", orderItemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    public void deleteOrderItem_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/order-items/{orderItemId}", orderItemId))
                .andExpect(status().isNoContent());

        assertThat(orderItemRepository.findById(orderItemId)).isEmpty();
    }
}

