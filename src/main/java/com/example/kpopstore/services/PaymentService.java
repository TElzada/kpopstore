package com.example.kpopstore.services;

import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.Payment;
import com.example.kpopstore.entities.Payment.PaymentStatus;
import com.example.kpopstore.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    public Payment createPayment(Order order, String transactionId, Payment.PaymentMethod paymentMethod) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setTransactionId(transactionId);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(PaymentStatus.PENDING);
        return paymentRepository.save(payment);
    }


    public Payment updatePaymentStatus(UUID paymentId, PaymentStatus status) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.setStatus(status);
            return paymentRepository.save(payment);
        }
        throw new RuntimeException("Payment not found with ID: " + paymentId);
    }


    public Payment getPaymentById(UUID paymentId) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        return paymentOptional.orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));
    }


    public Payment getPaymentByOrder(Order order) {
        return paymentRepository.findByOrder(order).orElseThrow(() -> new RuntimeException("No payment found for order: " + order.getId()));
    }
}

