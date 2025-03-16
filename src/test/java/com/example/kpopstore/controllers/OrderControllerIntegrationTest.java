package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.User;
import com.example.kpopstore.entities.User.Role;
import com.example.kpopstore.repositories.OrderRepository;
import com.example.kpopstore.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    private UUID orderId;
    private Order order;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("testUser", "test@example.com", "password", Role.USER);
        user = userRepository.save(user);

        order = new Order(user, new ArrayList<>(), BigDecimal.valueOf(99.99), Order.OrderStatus.PENDING, LocalDateTime.now(), LocalDateTime.now());
        order = orderRepository.save(order);
        orderId = order.getId();
    }

    @Test
    public void getOrderById_ShouldReturnOrder() throws Exception {
        mockMvc.perform(get("/api/orders/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(99.99));
    }

    @Test
    public void deleteOrder_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/orders/{orderId}", orderId))
                .andExpect(status().isNoContent());

        assertThat(orderRepository.findById(orderId)).isEmpty();
    }
}

