package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.Payment;
import com.example.kpopstore.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private Payment payment;
    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order();
        order.setId(UUID.randomUUID());

        payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setOrder(order);
        payment.setTransactionId("txn_123");
        payment.setPaymentMethod(Payment.PaymentMethod.CARD);
        payment.setStatus(Payment.PaymentStatus.PENDING);

        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    public void getPaymentById_ShouldReturnPayment() throws Exception {
        when(paymentService.getPaymentById(eq(payment.getId()))).thenReturn(payment);

        mockMvc.perform(get("/api/payments/{paymentId}", payment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("txn_123"))
                .andExpect(jsonPath("$.paymentMethod").value("CREDIT_CARD"));
    }

    @Test
    public void createPayment_ShouldReturnCreatedPayment() throws Exception {
        when(paymentService.createPayment(eq(order), eq("txn_123"), eq(Payment.PaymentMethod.CARD)))
                .thenReturn(payment);

        mockMvc.perform(post("/api/payments")
                        .param("orderId", order.getId().toString())
                        .param("transactionId", "txn_123")
                        .param("paymentMethod", "CREDIT_CARD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("txn_123"))
                .andExpect(jsonPath("$.paymentMethod").value("CREDIT_CARD"));
    }

    @Test
    public void deletePayment_ShouldReturnNoContent() throws Exception {
        doNothing().when(paymentService).deletePayment(eq(payment.getId()));

        mockMvc.perform(delete("/api/payments/{paymentId}", payment.getId()))
                .andExpect(status().isNoContent());
    }
}
