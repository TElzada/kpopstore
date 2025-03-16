package com.example.kpopstore.mappers;

import com.example.kpopstore.DTO.PaymentDTO;
import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.Payment;
import com.example.kpopstore.entities.Payment.PaymentMethod;
import com.example.kpopstore.entities.Payment.PaymentStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class PaymentMapperTest {

    @Test
    void testToDTO() {
        Payment payment = new Payment(new Order(), PaymentMethod.CARD, PaymentStatus.PENDING, "transaction123");
        payment.setId(UUID.randomUUID());

        PaymentDTO paymentDTO = PaymentMapper.toDTO(payment);

        assertNotNull(paymentDTO);
        assertEquals(payment.getId(), paymentDTO.getId());
        assertEquals(payment.getTransactionId(), paymentDTO.getTransactionId());
    }

    @Test
    void testToEntity() {
        PaymentDTO paymentDTO = new PaymentDTO(UUID.randomUUID(), UUID.randomUUID(), "CREDIT_CARD", "PENDING", "transaction123");

        Payment payment = PaymentMapper.toEntity(paymentDTO);

        assertNotNull(payment);
        assertEquals(paymentDTO.getTransactionId(), payment.getTransactionId());
    }
}

