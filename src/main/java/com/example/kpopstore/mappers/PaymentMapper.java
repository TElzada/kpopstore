package com.example.kpopstore.mappers;

import com.example.kpopstore.DTO.PaymentDTO;
import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.Payment;
import com.example.kpopstore.entities.Payment.PaymentMethod;
import com.example.kpopstore.entities.Payment.PaymentStatus;
import com.example.kpopstore.entities.User;

public class PaymentMapper {

    public static PaymentDTO toDTO(Payment payment) {
        if (payment == null) {
            return null;
        }
        return new PaymentDTO(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getPaymentMethod().toString(),
                payment.getStatus().toString(),
                payment.getTransactionId()
        );
    }

    public static Payment toEntity(PaymentDTO paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }

        User user = new User("dummy", "dummy@email.com", "password", null);

        Order order = new Order(user, null, null, null, null, null);


        return new Payment(
                order,
                PaymentMethod.valueOf(paymentDTO.getPaymentMethod()),
                PaymentStatus.valueOf(paymentDTO.getStatus()),
                paymentDTO.getTransactionId()
        );
    }
}

