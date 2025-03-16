package com.example.kpopstore.bootstrap;

import com.example.kpopstore.entities.*;
import com.example.kpopstore.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.example.kpopstore.entities.Order.OrderStatus;
import com.example.kpopstore.entities.Payment.PaymentMethod;
import com.example.kpopstore.entities.Payment.PaymentStatus;
import com.example.kpopstore.entities.User.Role;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public void run(String... args) throws Exception {

        Album album1 = new Album("Wings", "BTS", LocalDate.of(2016, 10, 10), BigDecimal.valueOf(20.99), 100, "2nd FULL-LENGTH ALBUM", "cover1.jpg");
        Album album2 = new Album("HYYH", "BTS", LocalDate.of(2016, 5, 2), BigDecimal.valueOf(25.99), 50, "1st SPECIAL ALBUM", "cover2.jpg");
        Album album3 = new Album("MAP OF THE SOUL : PERSONA", "BTS", LocalDate.of(2019, 4, 12), BigDecimal.valueOf(24.89), 150, "6th MINI ALBUM", "cover3.jpg");
        Album album4 = new Album("Love Yourself:Her", "BTS", LocalDate.of(2017, 9, 18), BigDecimal.valueOf(17.95), 70, "5th MINI ALBUM", "cover4.jpg");
        Album album5 = new Album("Proof", "BTS", LocalDate.of(2022, 6, 10), BigDecimal.valueOf(69.99), 120, "5th MINI ALBUM", "cover5.jpg");
        albumRepository.saveAll(List.of(album1, album2, album3, album4, album5));


        User user1 = new User("user1", "user1@gmail.com", "12345", Role.USER);
        User user2 = new User("admin", "admin@gmail.com", "54321", Role.ADMIN);
        userRepository.saveAll(List.of(user1, user2));


        Order order1 = new Order(user1,new ArrayList<>(), BigDecimal.valueOf(41.98), OrderStatus.PENDING,LocalDateTime.of(2025, 2, 27, 12, 59), LocalDateTime.of(2025,2,27,13, 30));
        Order order2 = new Order(user1,new ArrayList<>(), BigDecimal.valueOf(20.98), OrderStatus.PAID,LocalDateTime.of(2024, 7, 4, 13, 2), LocalDateTime.of(2024,7,4,13, 10));
        orderRepository.saveAll(List.of(order1, order2));


        OrderItem orderItem1 = new OrderItem(order1, album1, 2);
        OrderItem orderItem2 = new OrderItem(order2, album2, 1);
        orderItemRepository.saveAll(List.of(orderItem1, orderItem2));


        Review review1 = new Review("Great album!", 5 , LocalDateTime.of(2019, 10, 2, 13, 45) , album1, user1 );
        Review review2 = new Review("Could be better honestly", 4 , LocalDateTime.of(2023, 1,2, 21, 25), album1, user1);
        reviewRepository.saveAll(List.of(review1, review2));


        Payment payment1 = new Payment(order1, PaymentMethod.CARD, PaymentStatus.COMPLETED, "txn123");
        Payment payment2 = new Payment(order2, PaymentMethod.PAYPAL, PaymentStatus.PENDING, "txn124");
        paymentRepository.saveAll(List.of(payment1, payment2));
    }
}
