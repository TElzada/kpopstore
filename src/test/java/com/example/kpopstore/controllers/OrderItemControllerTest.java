package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.OrderItem;
import com.example.kpopstore.services.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderItemService orderItemService;

    @InjectMocks
    private OrderItemController orderItemController;

    private OrderItem orderItem;

    @BeforeEach
    public void setUp() {
        orderItem = new OrderItem();
        orderItem.setId(UUID.randomUUID());
        orderItem.setQuantity(2);
        orderItem.setAlbum(null);
    }

    @Test
    public void getOrderItemById_ShouldReturnOrderItem() throws Exception {
        when(orderItemService.getOrderItemById(orderItem.getId())).thenReturn(orderItem);

        mockMvc.perform(get("/api/order-items/{orderItemId}", orderItem.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderItem.getId().toString()))
                .andExpect(jsonPath("$.quantity").value(orderItem.getQuantity()));
    }

    @Test
    public void createOrderItem_ShouldReturnCreatedOrderItem() throws Exception {
        when(orderItemService.createOrderItem(any(OrderItem.class))).thenReturn(orderItem);

        mockMvc.perform(post("/api/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"quantity\": 2, \"album\": null }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(orderItem.getQuantity()))
                .andExpect(jsonPath("$.id").value(orderItem.getId().toString()));
    }

    @Test
    public void deleteOrderItem_ShouldReturnNoContent() throws Exception {

        doNothing().when(orderItemService).deleteOrderItem(orderItem.getId());

        mockMvc.perform(delete("/api/order-items/{orderItemId}", orderItem.getId()))
                .andExpect(status().isNoContent());
    }

}

