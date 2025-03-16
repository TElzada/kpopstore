package com.example.kpopstore.DTO;

import java.util.UUID;

public class PaymentDTO {

    private UUID id;
    private UUID orderId;
    private String paymentMethod;
    private String status;
    private String transactionId;


    public PaymentDTO(UUID id, UUID orderId, String paymentMethod, String status, String transactionId) {
        this.id = id;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.transactionId = transactionId;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
