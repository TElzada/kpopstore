package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.Payment;
import com.example.kpopstore.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable UUID paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(
            @RequestParam UUID orderId,
            @RequestParam String transactionId,
            @RequestParam Payment.PaymentMethod paymentMethod) {

        Order order = new Order();
        order.setId(orderId);

        Payment createdPayment = paymentService.createPayment(order, transactionId, paymentMethod);

        return ResponseEntity.ok(createdPayment);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    }
}
