package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.Payment;
import com.example.kpopstore.repositories.OrderRepository;
import com.example.kpopstore.repositories.PaymentRepository;
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
public class PaymentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    private UUID paymentId;
    private Payment payment;
    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order();
        order = orderRepository.save(order);

        payment = new Payment(order, Payment.PaymentMethod.CARD, Payment.PaymentStatus.COMPLETED, "TXN123456");
        payment = paymentRepository.save(payment);
        paymentId = payment.getId();
    }

    @Test
    public void getPaymentById_ShouldReturnPayment() throws Exception {
        mockMvc.perform(get("/api/payments/{paymentId}", paymentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("TXN123456"));
    }

    @Test
    public void deletePayment_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/payments/{paymentId}", paymentId))
                .andExpect(status().isNoContent());

        assertThat(paymentRepository.findById(paymentId)).isEmpty();
    }
}
