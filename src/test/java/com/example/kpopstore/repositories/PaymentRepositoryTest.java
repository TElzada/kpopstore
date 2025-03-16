package com.example.kpopstore.repositories;

import com.example.kpopstore.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.math.BigDecimal;
import java.util.List;
import com.example.kpopstore.entities.Order.OrderStatus;
import com.example.kpopstore.entities.User.Role;
import com.example.kpopstore.entities.Payment.PaymentMethod;
import com.example.kpopstore.entities.Payment.PaymentStatus;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testSaveAndFindPayment() {

        Order order = new Order(new User("impala67", "dean.winchester@gmail.com", "password123", Role.USER),
                List.of(), BigDecimal.valueOf(41.98), OrderStatus.PENDING, LocalDateTime.now(), LocalDateTime.now());
        order = orderRepository.save(order);


        Payment payment = new Payment(order, PaymentMethod.CARD, PaymentStatus.COMPLETED, "12345TX");
        payment = paymentRepository.save(payment);

        Optional<Payment> foundPayment = paymentRepository.findById(payment.getId());

        assertThat(foundPayment).isPresent();
        assertThat(foundPayment.get().getTransactionId()).isEqualTo("12345TX");
    }
}

