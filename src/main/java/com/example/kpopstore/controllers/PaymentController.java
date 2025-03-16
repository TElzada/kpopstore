package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.Order;
import com.example.kpopstore.entities.Payment;
import com.example.kpopstore.exceptions.PaymentNotFoundException;
import com.example.kpopstore.exceptions.InvalidPaymentException;
import com.example.kpopstore.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        try {
            Payment payment = paymentService.getPaymentById(paymentId);
            return ResponseEntity.ok(payment);
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> createPayment(
            @RequestParam UUID orderId,
            @RequestParam String transactionId,
            @RequestParam Payment.PaymentMethod paymentMethod) {

        if (orderId == null || transactionId == null || paymentMethod == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All parameters are required.");
        }

        try {
            Order order = new Order();
            order.setId(orderId);

            Payment createdPayment = paymentService.createPayment(order, transactionId, paymentMethod);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
        } catch (InvalidPaymentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payment details.");
        }
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID paymentId) {
        try {
            paymentService.deletePayment(paymentId);
            return ResponseEntity.noContent().build();
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
