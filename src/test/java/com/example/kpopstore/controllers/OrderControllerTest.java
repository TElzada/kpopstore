package com.example.kpopstore.controllers;


import com.example.kpopstore.entities.Order;
import com.example.kpopstore.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order(/* параметры */);
    }

    @Test
    public void createOrder_ShouldReturnCreatedOrder() throws Exception {
        order.setStatus(Order.OrderStatus.PENDING);
        order.setTotalPrice(new BigDecimal("100.00"));

        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"user\": { \"id\": \"123\" }, \"status\": \"PENDING\", \"totalPrice\": 100.00 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.totalPrice").value(100.00));
    }


    @Test
    public void updateOrder_ShouldReturnUpdatedOrder() throws Exception {
        order.setStatus(Order.OrderStatus.SHIPPED);
        when(orderService.updateOrder(eq(order.getId()), any(Order.class))).thenReturn(order);

        mockMvc.perform(put("/orders/{orderId}", order.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"status\": \"SHIPPED\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SHIPPED"));
    }


    @Test
    public void deleteOrder_ShouldReturnNoContent() throws Exception {
        doNothing().when(orderService).deleteOrder(order.getId());

        mockMvc.perform(delete("/orders/{orderId}", order.getId()))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrder(order.getId());
    }

    @Test
    public void getOrderById_ShouldReturnOrder() throws Exception {
        when(orderService.getOrderById(order.getId())).thenReturn(order);

        mockMvc.perform(get("/orders/{orderId}", order.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PENDING"));
    }
}
